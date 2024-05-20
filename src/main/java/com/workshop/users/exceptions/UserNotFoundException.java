package com.workshop.users.exceptions;

public class UserNotFoundException extends RuntimeException {
    private String message;
    private Long id;
    public UserNotFoundException(Long id) {
        this.id = id;
    }
    public UserNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return "The user with the id " + id + " was not found";
    }



}
