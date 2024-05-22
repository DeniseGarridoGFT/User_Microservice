package com.workshop.users.exceptions;


public class ProductNotFoundException extends Exception{
    private String message;

    public ProductNotFoundException(String message){
        super();
        this.message = message;
    }

}
