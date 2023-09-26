package com.tc.example.chatapplication.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class GroupChatRequest {

    private List<Integer> userIds;

    private String chat_name;

    private String chat_image;

}
