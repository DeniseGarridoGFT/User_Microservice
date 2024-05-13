package com.workshop.users.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomException extends Exception{
    private String message;
    private HttpStatus status;

    public CustomException(String message) {
        super(message);
        this.status = status;
    }
}
