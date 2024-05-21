package com.workshop.users.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<MyResponseException> handleProductNotFoundUserException(NotFoundUserException ex) {
        MyResponseException myResponseException = MyResponseException.builder()
                .code(HttpStatus.NOT_FOUND)
                .message("The user with this id don't exists.")
                .build();
        return new ResponseEntity<>(myResponseException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticateException.class)
    public ResponseEntity<MyResponseException> handleInvalidProductIdException(AuthenticateException ex) {
        MyResponseException myResponseException = MyResponseException.builder()
                .code(HttpStatus.NOT_FOUND)
                .message("The email or the password are incorrect.")
                .build();
        return new ResponseEntity<>(myResponseException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictWishListException.class)
    public ResponseEntity<MyResponseException> handleGeneralException(ConflictWishListException ex) {
        MyResponseException myResponseException = MyResponseException.builder()
                .code(HttpStatus.CONFLICT)
                .message("One product already on the wish list.")
                .build();
        return new ResponseEntity<>(myResponseException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundProductException.class)
    public ResponseEntity<MyResponseException> handleGeneralException(NotFoundProductException ex) {
        MyResponseException myResponseException = MyResponseException.builder()
                .code(HttpStatus.NOT_FOUND)
                .message("One id of product not exists.")
                .build();
        return new ResponseEntity<>(myResponseException, HttpStatus.NOT_FOUND);
    }
}