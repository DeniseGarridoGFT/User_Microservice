package com.workshop.users.exceptions;

import org.springframework.http.HttpStatus;

public class PasswordDoentMatchException extends RuntimeException{
    private String message;
    public static final HttpStatus STATUS_CODE = HttpStatus.UNAUTHORIZED;

    public PasswordDoentMatchException(String message){
        super(message);
        this.message = message;
    }
}
