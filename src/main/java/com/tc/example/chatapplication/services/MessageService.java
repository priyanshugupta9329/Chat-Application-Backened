package com.tc.example.chatapplication.services;


import com.tc.example.chatapplication.exception.ChatException;
import com.tc.example.chatapplication.exception.MessageException;
import com.tc.example.chatapplication.exception.UserException;
import com.tc.example.chatapplication.model.Message;
import com.tc.example.chatapplication.model.User;
import com.tc.example.chatapplication.request.SendmessageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {

    public Message sendMessage(SendmessageRequest req) throws UserException, ChatException;

    public List<Message> getChatMessage(Integer chatId, User reqUser) throws ChatException, UserException;

    public Message findMessageById(Integer messageId) throws MessageException;

    public void deleteMessage(Integer messageId, User reqUser) throws MessageException, UserException;
}
