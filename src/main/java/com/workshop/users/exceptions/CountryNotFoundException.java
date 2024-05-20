package com.workshop.users.exceptions;

public class CountryNotFoundException extends RuntimeException{
    private String message;

    public CountryNotFoundException(String message) {
        this.message = message;
    }
}
