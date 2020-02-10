package com.ipiecoles.java.mdd050.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

/*Error 404 Exception*/
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEntityNotFoundException (
            EntityNotFoundException entityNotFoundException){
     return entityNotFoundException.getMessage();
    }

/* Error 400 Exception*/
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException (
            IllegalArgumentException illegalArgumentException){
        return illegalArgumentException.getMessage();
    }

}
