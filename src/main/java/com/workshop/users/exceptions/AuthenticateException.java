package com.workshop.users.exceptions;

public class AuthenticateException extends RuntimeException{
    private final String message;
    public AuthenticateException(String message){
        this.message = message;
    }
}
