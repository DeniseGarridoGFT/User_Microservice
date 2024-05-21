package com.workshop.users.exceptions;

public class CountryServiceException extends RuntimeException {
    public CountryServiceException(String message) {
        super(message);
    }
}