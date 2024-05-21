package com.workshop.users.exceptions;

public class RegisterException extends RuntimeException{
    private String message;

    public RegisterException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return "User can't be registered";
    }

}
