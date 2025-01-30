package com.esprit.socialNetwork.exceptions;

public class StorageFileNotFoundException extends InvalidEntityBaseException {

    public StorageFileNotFoundException(String fileName) {
        super("file", fileName, "file not found");
    }
}
