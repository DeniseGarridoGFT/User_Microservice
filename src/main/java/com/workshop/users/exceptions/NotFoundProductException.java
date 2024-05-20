package com.workshop.users.exceptions;


public class NotFoundProductException extends Exception{
    private String message;

    public NotFoundProductException(String message){
        super();
        this.message = message;
    }

}
