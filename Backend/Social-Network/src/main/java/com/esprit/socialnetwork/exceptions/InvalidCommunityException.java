package com.esprit.socialnetwork.exceptions;

public class InvalidCommunityException extends InvalidEntityBaseException {

    public InvalidCommunityException(String id, String cause) {
        super("community", id, cause);
    }
}
