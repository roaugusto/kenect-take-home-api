package com.kenect.contact.aggregator.exception;

import lombok.Getter;

@Getter
public class InternalServerErrorException extends RuntimeException {

    private final String message;

    public InternalServerErrorException(String message) {
        this.message = message;
    }

}
