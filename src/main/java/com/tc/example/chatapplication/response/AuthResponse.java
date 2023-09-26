package com.tc.example.chatapplication.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String jwt;

    private boolean isAuth;
}
