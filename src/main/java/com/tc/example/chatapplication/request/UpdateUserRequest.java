package com.tc.example.chatapplication.request;

import lombok.Data;

@Data
public class UpdateUserRequest {

    private String full_name;
    private String profile_picture;
}
