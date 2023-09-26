package com.tc.example.chatapplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetail> UserExceptionHandler(UserException e, WebRequest req){

        ErrorDetail err = new ErrorDetail(e.getMessage(), req.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<ErrorDetail>(err, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MessageException.class)
    public ResponseEntity<ErrorDetail> MessageExceptionHandler(MessageException ue, WebRequest req){

        ErrorDetail err = new ErrorDetail(ue.getMessage(), req.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<ErrorDetail>(err, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ChatException.class)
    public ResponseEntity<ErrorDetail> ChatExceptionHandler(ChatException ue, WebRequest req){

        ErrorDetail err = new ErrorDetail(ue.getMessage(), req.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<ErrorDetail>(err, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetail> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException me, WebRequest req){

        ErrorDetail err = new ErrorDetail(me.getMessage(), req.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<ErrorDetail>(err, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorDetail> handleNoHandlerFoundExcept(NoHandlerFoundException ex, WebRequest req){

        ErrorDetail error = new ErrorDetail("Endpoint not found",ex.getMessage(), LocalDateTime.now());

        return new ResponseEntity<ErrorDetail>(error, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> otherExceptionHandler(UserException e, WebRequest req){

        ErrorDetail err = new ErrorDetail(e.getMessage(), req.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<ErrorDetail>(err, HttpStatus.BAD_REQUEST);

    }
}
