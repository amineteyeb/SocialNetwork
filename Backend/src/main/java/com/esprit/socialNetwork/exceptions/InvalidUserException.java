package com.esprit.socialNetwork.exceptions;

public class InvalidUserException extends InvalidEntityBaseException {
    public InvalidUserException(String id, String cause) {
        super("user", id, cause);
    }
}
