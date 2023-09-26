package com.tc.example.chatapplication.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class SendmessageRequest {

    private Integer userId;
    private Integer chatId;
    private String content;
}
