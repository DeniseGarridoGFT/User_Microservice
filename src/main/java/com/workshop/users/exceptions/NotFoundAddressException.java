package com.workshop.users.exceptions;

public class NotFoundAddressException extends RuntimeException{
    private String message;

    public NotFoundAddressException(String message){
        super(message);
        this.message = message;
    }

}

