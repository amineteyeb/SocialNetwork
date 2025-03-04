package com.esprit.socialnetwork.exceptions;

public class InvalidUserException extends InvalidEntityBaseException {
    public InvalidUserException(String id, String cause) {
        super("user", id, cause);
    }
}
