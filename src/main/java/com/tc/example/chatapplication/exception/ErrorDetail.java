package com.tc.example.chatapplication.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ErrorDetail {

    private String error;
    private String message;

    private LocalDateTime timeStamp;


}
