package com.javaprojects.crudapp.exceptionhandler;


import com.javaprojects.crudapp.exception.InvalidUserException;
import com.javaprojects.crudapp.exception.errors.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UserExceptionHandlerAdvice {


    @ExceptionHandler(InvalidUserException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleInvalidUserException(InvalidUserException userException){
        ErrorResponse errorResponse = new ErrorResponse();
        log.error("InvalidUserException thrown");
        errorResponse.setMessage("User not found :");
        return errorResponse;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleException(Exception ex){
        ErrorResponse errorResponse = new ErrorResponse();
        log.error("Exception thrown");
        errorResponse.setMessage("Exception ->"+ex.getMessage());
        return errorResponse;
    }


}
