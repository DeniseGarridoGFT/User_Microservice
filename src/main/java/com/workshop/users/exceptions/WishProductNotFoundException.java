package com.workshop.users.exceptions;

public class WishProductNotFoundException extends Exception{
    private String message;

    public WishProductNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
