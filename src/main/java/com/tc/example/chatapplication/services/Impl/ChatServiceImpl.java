package com.tc.example.chatapplication.services.Impl;

import com.tc.example.chatapplication.exception.ChatException;
import com.tc.example.chatapplication.exception.UserException;
import com.tc.example.chatapplication.model.Chat;
import com.tc.example.chatapplication.model.User;
import com.tc.example.chatapplication.repository.ChatRepository;
import com.tc.example.chatapplication.request.GroupChatRequest;
import com.tc.example.chatapplication.services.ChatService;
import com.tc.example.chatapplication.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {

    private ChatRepository chatRepository;
    private UserService userService;

    public ChatServiceImpl(ChatRepository chatRepository, UserService userService) {
        this.chatRepository = chatRepository;
        this.userService = userService;
    }

    @Override
    public Chat createChat(User reqUser, Integer userId2) throws UserException {

        User user = userService.findUserById(userId2);
        Chat isChatExist = chatRepository.findSingleChatByUserIds(user, reqUser);

        if(isChatExist != null){
            return isChatExist;
        }

        Chat chat = new Chat();
        chat.setCreated_by(reqUser);
        chat.getUsers().add(user);
        chat.getUsers().add(reqUser);
        chat.setGroup(false);

        chatRepository.save(chat);

        return chat;
    }

    @Override
    public Chat findChatById(Integer chatId) throws ChatException {

        Optional<Chat> chat = chatRepository.findById(chatId);

        if(chat.isPresent()){
            return chat.get();
        }
        throw new ChatException("Chat not found with id "+ chatId);
    }

    @Override
    public List<Chat> findAllChatByUserId(Integer userId) throws UserException {

        User user = userService.findUserById(userId);
        List<Chat> chats = chatRepository.findChatByUserId(user.getId());

        return chats;
    }

    @Override
    public Chat createGroup(GroupChatRequest req, User reqUser) throws UserException {

        Chat group = new Chat();
        group.setGroup(true);
        group.setChat_image(req.getChat_image());
        group.setChat_name(req.getChat_name());
        group.setCreated_by(reqUser);
        group.getAdmins().add(reqUser);

        for(Integer userId: req.getUserIds()){
            User user = userService.findUserById(userId);
            group.getUsers().add(user);
        }

        return group;
    }

    @Override
    public Chat addUserToGroup(Integer userId, Integer chatId, User reqUser) throws UserException, ChatException {
        Optional<Chat> opt = chatRepository.findById(chatId);

        User user = userService.findUserById(userId);
        if(opt.isPresent()){

            Chat chat = opt.get();
            if(chat.getAdmins().contains(reqUser)){
                chat.getUsers().add(user);
                return chatRepository.save(chat);
            }
            else{
                throw new UserException("You are not admin");
            }

        }
        throw new ChatException("chat not found with id "+chatId);
    }

    @Override
    public Chat renameGroup(Integer chatId, String groupName, User reqUser) throws ChatException, UserException {

        Optional<Chat> opt = chatRepository.findById(chatId);
        if(opt.isPresent()){
            Chat chat = opt.get();
            if(chat.getUsers().contains(reqUser)){
                chat.setChat_name(groupName);
                return chatRepository.save(chat);
            }
            throw new UserException("You are not member of this group");
        }

        throw new ChatException("chat not found with id "+chatId);
    }

    @Override
    public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws UserException, ChatException {

        Optional<Chat> opt = chatRepository.findById(chatId);

        User user = userService.findUserById(userId);
        if(opt.isPresent()){

            Chat chat = opt.get();
            if(chat.getAdmins().contains(reqUser)){
                chat.getUsers().add(user);
                return chatRepository.save(chat);
            }
            else if(chat.getUsers().contains(reqUser)){
                if(user.getId().equals(reqUser.getId())){
                    chat.getUsers().add(user);
                    return chatRepository.save(chat);
                }
            }
            else{
                throw new UserException("You can't remove another user");
            }

        }
        throw new ChatException("chat not found with id "+chatId);
    }

    @Override
    public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {
        Optional<Chat> opt = chatRepository.findById(chatId);

        if(opt.isPresent()){
            Chat chat = opt.get();
            chatRepository.deleteById(chat.getId());
        }
    }
}
