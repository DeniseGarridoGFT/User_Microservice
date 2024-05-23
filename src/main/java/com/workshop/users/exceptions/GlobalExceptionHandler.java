package com.workshop.users.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<MyResponseError> handleProductNotFoundUserException(NotFoundUserException ex) {
        MyResponseError myResponseError = MyResponseError.builder()
                .code(HttpStatus.NOT_FOUND)
                .message("The user with this id don't exists.")
                .build();
        return new ResponseEntity<>(myResponseError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundAddressException.class)
    public ResponseEntity<MyResponseError> handleProductNotFoundAddressException(NotFoundAddressException ex) {
        MyResponseError myResponseError = MyResponseError.builder()
                .code(HttpStatus.NOT_FOUND)
                .message("The address with this id don't exists.")
                .build();
        return new ResponseEntity<>(myResponseError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticateException.class)
    public ResponseEntity<MyResponseError> handleInvalidProductIdException(AuthenticateException ex) {
        MyResponseError myResponseError = MyResponseError.builder()
                .code(HttpStatus.NOT_FOUND)
                .message("The email or the password are incorrect.")
                .build();
        return new ResponseEntity<>(myResponseError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundWishProductException.class)
    public ResponseEntity<MyResponseError> handleNotFoundWishProductException(NotFoundWishProductException ex) {
        MyResponseError myResponseError = MyResponseError.builder()
                .code(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(myResponseError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<MyResponseError> handleUserValidationException(UserValidationException ex) {
        MyResponseError myResponseError = MyResponseError.builder()
                .code(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(myResponseError, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NotFoundProductException.class)
    public ResponseEntity<MyResponseError> handleGeneralException(NotFoundProductException ex) {
        MyResponseError myResponseError = MyResponseError.builder()
                .code(HttpStatus.NOT_FOUND)
                .message("One id of product not exists.")
                .build();
        return new ResponseEntity<>(myResponseError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictWishListException.class)
    public ResponseEntity<MyResponseError> handleConflictWishListException(ConflictWishListException ex) {
        MyResponseError myResponseError = MyResponseError.builder()
                .code(HttpStatus.CONFLICT)
                .message("One id of product not exists.")
                .build();
        return new ResponseEntity<>(myResponseError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<MyResponseError> handleCountryNotFoundException(CountryNotFoundException ex) {
        MyResponseError myResponseError = MyResponseError.builder()
                .code(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(myResponseError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RegisterException.class)
    public ResponseEntity<MyResponseError> handleRegisterException(RegisterException ex) {
        MyResponseError myResponseError = MyResponseError.builder()
                .code(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(myResponseError, HttpStatus.BAD_REQUEST);
    }

}
