package com.esprit.socialnetwork.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidPromoCodeException extends RuntimeException {
    public InvalidPromoCodeException(String codeId, String message) {
        super(codeId + " " + message);
    }
}
