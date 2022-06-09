package com.musicstore.musicstorecatalog.exception;

public class UnprocessableRequestException extends RuntimeException {

    public UnprocessableRequestException(String message) {
        super(message);
    }

    public UnprocessableRequestException() {
        super();
    }

}