package com.tc.example.chatapplication.services;

import com.tc.example.chatapplication.exception.UserException;
import com.tc.example.chatapplication.model.User;
import com.tc.example.chatapplication.request.UpdateUserRequest;
import jdk.jshell.spi.ExecutionControl;

import java.util.List;

public interface UserService {

    public User findUserById(Integer id) throws UserException;

    public User findUserProfile(String jwt) throws UserException;

    public User updateUser(Integer userId, UpdateUserRequest req) throws UserException;

    public List<User> searchUser(String query);
}
