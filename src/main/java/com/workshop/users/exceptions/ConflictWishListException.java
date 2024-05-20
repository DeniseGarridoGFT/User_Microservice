package com.workshop.users.exceptions;

public class ConflictWishListException extends Exception{
    private String message;

    public ConflictWishListException(String message){
        this.message = message;
    }
}
