package com.workshop.users.exceptions;

public class InternalServerException extends Exception{
    private String message;

    public InternalServerException(String message) {
        super(message);
        this.message = message;
    }
}
