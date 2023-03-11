package com.gg.cnt.security;

import com.gg.cnt.helper.SecurityHelper;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        String userName = SecurityHelper.getCurrentUsername();
        return Optional.of(Objects.nonNull(userName) ? userName : "SYSTEM");
    }
}
