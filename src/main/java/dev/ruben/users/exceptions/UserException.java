package dev.ruben.users.exceptions;


public abstract class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }
}

