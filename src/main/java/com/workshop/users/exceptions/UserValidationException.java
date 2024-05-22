package com.workshop.users.exceptions;

import lombok.Data;

@Data
public class UserValidationException extends Exception{
    private String message;

    public UserValidationException(String message) {
        super(message);
        this.message = message;
    }
}
