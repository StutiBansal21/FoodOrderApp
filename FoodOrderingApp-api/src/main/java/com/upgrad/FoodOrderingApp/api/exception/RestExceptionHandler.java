package com.upgrad.FoodOrderingApp.api.exception;

import com.upgrad.FoodOrderingApp.api.model.ErrorResponse;
import com.upgrad.FoodOrderingApp.service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(SignUpRestrictedException.class)
    public ResponseEntity<ErrorResponse>signUpRestrictedException(SignUpRestrictedException exc, WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorResponse>authenticationFailedException(AuthenticationFailedException exc,WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()),HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UpdateCustomerException.class)
    public ResponseEntity<ErrorResponse>updateCustomerException(UpdateCustomerException exc,WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()),HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse>updateCustomerException(CategoryNotFoundException exc,WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()),HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ErrorResponse>addressNotFoundException(AddressNotFoundException exc,WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthorizationFailedException.class)
    public ResponseEntity<ErrorResponse>authorizationFailedException(AuthorizationFailedException exc,WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()),HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse>customerNotFoundException(CategoryNotFoundException exc,WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorResponse>itemNotFoundException(ItemNotFoundException exc,WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ErrorResponse>restaurantNotFoundException(RestaurantNotFoundException exc,WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SaveAddressException.class)
    public ResponseEntity<ErrorResponse>invalidAddressRequestFieldException(SaveAddressException exc,WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()),HttpStatus.BAD_REQUEST);
    }


}
