package com.gg.cnt.errors;

public class ValidatorException extends RuntimeException {
    private final String fieldName;
    public ValidatorException(final String message, final String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }

    public ValidatorException(final String fieldName) {
        super("invalid");
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
