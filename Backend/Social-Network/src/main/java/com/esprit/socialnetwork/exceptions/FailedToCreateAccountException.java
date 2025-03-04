package com.esprit.socialnetwork.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FailedToCreateAccountException extends Exception  {
    private final String errorMessage;
    private String errorCode;


    public FailedToCreateAccountException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.errorMessage = errorMessage;
    }


    public FailedToCreateAccountException(String errorMessage, String errorCode, Throwable cause) {
        super(errorMessage, cause);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }


    public FailedToCreateAccountException(String s) {

        this.errorMessage = s;
    }
}
