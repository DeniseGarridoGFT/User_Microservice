package com.workshop.users.exceptions;

public class RegisterException extends RuntimeException{
    private String message;

    public RegisterException(String message) {
        super(message);
        this.message = message;
    }


}
