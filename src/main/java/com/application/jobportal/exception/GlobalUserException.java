package com.application.jobportal.exception;

import com.application.jobportal.utility.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.atomic.AtomicInteger;

@ControllerAdvice
@ResponseBody
public class GlobalUserException extends Exception{

    @Autowired
    Response response;
    @ExceptionHandler
    public ResponseEntity<Response> exceptionMessage (UserException exception){
        response.setMessage(exception.getMessage());
        return new ResponseEntity<>(response,exception.status);
    }
}
