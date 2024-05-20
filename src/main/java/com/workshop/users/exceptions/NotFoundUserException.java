package com.workshop.users.exceptions;

public class NotFoundUserException extends Exception{
    private String message;

    public NotFoundUserException(String message){
        this.message = message;
    }
}
