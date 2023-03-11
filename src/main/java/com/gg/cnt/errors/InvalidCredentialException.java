package com.gg.cnt.errors;

public class InvalidCredentialException extends ValidatorException {
    public InvalidCredentialException() {
        super("invalidUsernameOrPassword");
    }
}
