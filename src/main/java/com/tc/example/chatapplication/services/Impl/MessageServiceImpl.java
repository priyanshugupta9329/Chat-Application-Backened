package com.tc.example.chatapplication.services.Impl;

import com.tc.example.chatapplication.exception.ChatException;
import com.tc.example.chatapplication.exception.MessageException;
import com.tc.example.chatapplication.exception.UserException;
import com.tc.example.chatapplication.model.Chat;
import com.tc.example.chatapplication.model.Message;
import com.tc.example.chatapplication.model.User;
import com.tc.example.chatapplication.repository.MessageRepository;
import com.tc.example.chatapplication.request.SendmessageRequest;
import com.tc.example.chatapplication.services.ChatService;
import com.tc.example.chatapplication.services.MessageService;
import com.tc.example.chatapplication.services.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class MessageServiceImpl implements MessageService {

    private MessageRepository messageRepository;

    private UserService userService;

    private ChatService chatService;

    public MessageServiceImpl(MessageRepository messageRepository, UserService userService, ChatService chatService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.chatService = chatService;
    }

    @Override
    public Message sendMessage(SendmessageRequest req) throws UserException, ChatException {

        User user = userService.findUserById(req.getUserId());
        Chat chat = chatService.findChatById(req.getChatId());

        Message message = new Message();
        message.setChat(chat);
        message.setUser(user);
        message.setContent(req.getContent());
        message.setTimestamp(LocalDateTime.now());
        messageRepository.save(message);

        return message;
    }

    @Override
    public List<Message> getChatMessage(Integer chatId, User reqUser) throws ChatException, UserException {
        Chat chat = chatService.findChatById(chatId);

        if(!chat.getUsers().contains(reqUser)){
            throw new UserException("You are not related to this chat "+chat.getId());
        }
        List<Message> messages = messageRepository.findByChatId(chat.getId());
        return messages;

    }

    @Override
    public Message findMessageById(Integer messageId) throws MessageException {

        Optional<Message> opt = messageRepository.findById(messageId);

        if(opt.isPresent()){
            return opt.get();
        }

        throw new MessageException("Message not found with id "+messageId);
    }

    @Override
    public void deleteMessage(Integer messageId, User reqUser) throws MessageException, UserException {

        Message message = findMessageById(messageId);

        if(message.getUser().getId().equals(reqUser.getId())){
            messageRepository.deleteById(messageId);
        }

        throw new UserException("You can't delete another user's message " +reqUser.getFull_name());
    }
}
