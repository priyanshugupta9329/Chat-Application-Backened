package com.tc.example.chatapplication.controller;


import com.tc.example.chatapplication.exception.ChatException;
import com.tc.example.chatapplication.exception.MessageException;
import com.tc.example.chatapplication.exception.UserException;
import com.tc.example.chatapplication.model.Message;
import com.tc.example.chatapplication.model.User;
import com.tc.example.chatapplication.request.SendmessageRequest;
import com.tc.example.chatapplication.response.ApiResponse;
import com.tc.example.chatapplication.services.MessageService;
import com.tc.example.chatapplication.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private MessageService messageService;

    private UserService userService;

    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }


    @PostMapping("/create")
    public ResponseEntity<Message> sendMessageHandler(@RequestBody SendmessageRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ChatException {

        User user = userService.findUserProfile(jwt);
        req.setUserId(user.getId());
        Message message = messageService.sendMessage(req);

        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getChatsMessageHandler(@PathVariable Integer chatId, @RequestHeader("Authorization") String jwt) throws UserException, ChatException {

        User user = userService.findUserProfile(jwt);

        List<Message> messages = messageService.getChatMessage(chatId,user);

        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @DeleteMapping ("/{messageId}")
    public ResponseEntity<ApiResponse> deleteMessageHandler(@PathVariable Integer messageId, @RequestHeader("Authorization") String jwt) throws UserException, ChatException, MessageException {

        User user = userService.findUserProfile(jwt);

       messageService.deleteMessage(messageId,user);
       ApiResponse res = new ApiResponse("message deleted successfully", false);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
