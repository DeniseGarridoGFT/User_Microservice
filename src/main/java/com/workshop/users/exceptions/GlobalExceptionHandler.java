package com.workshop.users.exceptions;

import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<MyResponseException> handleAddressNotFoundException(AddressNotFoundException ex) {
        MyResponseException myResponseException = MyResponseException.builder()
                .code(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(myResponseException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AddressServiceException.class)
    public ResponseEntity<MyResponseException> handleAddresServiceException(AddressServiceException ex) {
        MyResponseException myResponseException = MyResponseException.builder()
                .code(HttpStatus.CONFLICT)
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(myResponseException, HttpStatus.CONFLICT);
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
    public ResponseEntity<MyResponseException> handleConflictWishListException(ConflictWishListException ex) {
        MyResponseException myResponseException = MyResponseException.builder()
                .code(HttpStatus.CONFLICT)
                .message("One id of product doesn't exist.")
                .build();
        return new ResponseEntity<>(myResponseException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<MyResponseException> handleCountryNotFoundException(CountryNotFoundException ex) {
        MyResponseException myResponseException = MyResponseException.builder()
                .code(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(myResponseException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CountryServiceException.class)
    public ResponseEntity<MyResponseException> handleCountryServiceException(CountryServiceException ex) {
        MyResponseException myResponseException = MyResponseException.builder()
                .code(HttpStatus.CONFLICT)
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(myResponseException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<MyResponseException> handleInternalServerException(InternalServerException ex) {
        MyResponseException myResponseException = MyResponseException.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(myResponseException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<MyResponseException> handleGeneralException(ProductNotFoundException ex) {
        MyResponseException myResponseException = MyResponseException.builder()
                .code(HttpStatus.NOT_FOUND)
                .message("One id of product doesn't exist.")
                .build();
        return new ResponseEntity<>(myResponseException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<MyResponseException> handleUserNotFoundException(UserNotFoundException ex) {
        MyResponseException myResponseException = MyResponseException.builder()
                .code(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(myResponseException, HttpStatus.NOT_FOUND);
    }

//    we have to check what exception we want to remove
    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<MyResponseException> handleProductNotFoundUserException(NotFoundUserException ex) {
        MyResponseException myResponseException = MyResponseException.builder()
                .code(HttpStatus.NOT_FOUND)
                .message("The user with this id doesn't exist.")
                .build();
        return new ResponseEntity<>(myResponseException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<MyResponseException> handleValidationException(ValidationException ex) {
        MyResponseException myResponseException = MyResponseException.builder()
                .code(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(myResponseException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WishProductNotFoundException.class)
    public ResponseEntity<MyResponseException> handleNotFoundWishProductException(WishProductNotFoundException ex) {
        MyResponseException myResponseException = MyResponseException.builder()
                .code(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(myResponseException, HttpStatus.NOT_FOUND);
    }

}
