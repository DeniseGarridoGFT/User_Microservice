package com.workshop.users.exceptions;


public class NotFoundProductException extends RuntimeException{
    private String message;

    public NotFoundProductException(String message){
        super(message);
        this.message = message;
    }

}
