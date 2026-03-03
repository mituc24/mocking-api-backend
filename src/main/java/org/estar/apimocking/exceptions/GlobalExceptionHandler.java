package org.estar.apimocking.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<Map<String,Object>> handleAlreadyExistResource(ResourceAlreadyExistException ex)
    {
        Map<String,Object> body = new HashMap<>();
        body.put("statusCode","101");
        body.put("message",ex.getLocalizedMessage());
        return new ResponseEntity<>(body,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleResourceNotFound(ResourceNotFoundException ex)
    {
        Map<String,Object> body = new HashMap<>();
        body.put("message",ex.getLocalizedMessage());
        return new ResponseEntity<> (body, HttpStatus.NOT_FOUND);
    }


}
