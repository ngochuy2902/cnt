package com.gg.cnt.errors;

public class ObjectNotFoundException extends ValidatorException{
    public ObjectNotFoundException(String message, String fieldName) {
        super(message, fieldName);
    }

    public ObjectNotFoundException(String fieldName) {
        super(fieldName);
    }
}
