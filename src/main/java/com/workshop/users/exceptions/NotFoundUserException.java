package com.workshop.users.exceptions;

public class NotFoundUserException extends Exception{
    private final String message;

    public NotFoundUserException(String message){
        super(message);
        this.message = message;
    }
}
