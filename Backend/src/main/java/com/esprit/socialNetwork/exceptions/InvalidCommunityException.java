package com.esprit.socialNetwork.exceptions;

public class InvalidCommunityException extends InvalidEntityBaseException {

    public InvalidCommunityException(String id, String cause) {
        super("community", id, cause);
    }
}
