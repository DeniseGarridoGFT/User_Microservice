package com.workshop.users.exceptions;

public class NotFoundWishProductException extends Exception{
    private String message;

    public NotFoundWishProductException(String message) {
        super(message);
        this.message = message;
    }
}
