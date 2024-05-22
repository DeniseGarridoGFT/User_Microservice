package com.workshop.users.exceptions;

public class AddressNotFoundException extends Exception {
    private String message;

    public AddressNotFoundException(String message) {
        super(message);
        this.message = message;
    }

}
