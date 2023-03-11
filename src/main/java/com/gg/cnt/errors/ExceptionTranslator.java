package com.gg.cnt.errors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.violations.ConstraintViolationProblem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.text.MessageFormat;
import java.util.Objects;

import static com.gg.cnt.errors.ExceptionTranslator.ProblemKey.ERROR_FIELD;
import static com.gg.cnt.errors.ExceptionTranslator.ProblemKey.MESSAGE;
import static com.gg.cnt.errors.ExceptionTranslator.ProblemKey.PATH;
import static com.gg.cnt.errors.ExceptionTranslator.ProblemKey.VIOLATIONS;

@Slf4j
@ControllerAdvice
public class ExceptionTranslator implements ProblemHandling {
    /**
     * Post-process Problem payload to add the message key for front-end if needed
     */
    @Override
    public ResponseEntity<Problem> process(@Nullable final ResponseEntity<Problem> entity,
                                           @Nonnull final NativeWebRequest request) {
        if (entity == null || entity.getBody() == null) {
            return entity;
        }

        Problem problem = entity.getBody();
        if (!(problem instanceof ConstraintViolationProblem || problem instanceof DefaultProblem)) {
            return entity;
        }

        ProblemBuilder builder = Problem.builder()
                .withType(problem.getType())
                .withStatus(problem.getStatus())
                .withTitle(problem.getTitle())
                .with(PATH, Objects
                        .requireNonNull(request.getNativeRequest(HttpServletRequest.class))
                        .getRequestURI());

        if (problem instanceof ConstraintViolationProblem) {
            builder.with(VIOLATIONS, ((ConstraintViolationProblem) problem).getViolations())
                    .with(MESSAGE, "error.validator");
        } else {
            builder.withCause(((DefaultProblem) problem).getCause())
                    .withDetail(problem.getDetail())
                    .withInstance(problem.getInstance());
            problem.getParameters().forEach(builder::with);
            if (!problem.getParameters().containsKey(MESSAGE) && problem.getStatus() != null) {
                builder.with(MESSAGE, MessageFormat.format("{0}.{1}",
                        "error.http",
                        problem.getStatus().getStatusCode()));
            }
        }

        return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
    }

    @ExceptionHandler(ValidatorException.class)
    public ResponseEntity<Problem> handleValidator(final ValidatorException ex,
                                                   final NativeWebRequest request) {
        Problem problem = Problem.builder()
                .withType(URI.create("error.validator"))
                .withStatus(defaultConstraintViolationStatus())
                .with(MESSAGE, MessageFormat.format("{0}.{1}.{2}",
                        "error.validator",
                        ex.getFieldName(),
                        ex.getMessage()))
                .with(ERROR_FIELD, ex.getFieldName())
                .build();
        return create(ex, problem, request);
    }

    public static class ProblemKey {
        public static final String PATH = "path";
        public static final String MESSAGE = "message";
        public static final String VIOLATIONS = "violations";
        public static final String ERROR_FIELD = "error_field";
    }
}
