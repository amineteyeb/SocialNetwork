package com.esprit.socialnetwork.exceptions;

public class InvalidProductCategoryException extends InvalidEntityBaseException {
    public InvalidProductCategoryException(String id, String cause) {
        super("category", id, cause);
    }
}