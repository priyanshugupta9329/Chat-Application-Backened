package com.tc.example.chatapplication.controller;


import com.tc.example.chatapplication.exception.UserException;
import com.tc.example.chatapplication.model.User;
import com.tc.example.chatapplication.request.UpdateUserRequest;
import com.tc.example.chatapplication.response.ApiResponse;
import com.tc.example.chatapplication.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<User>  getUserProfileHandler(@RequestHeader("Authorization") String token) throws UserException {

        User user = userService.findUserProfile(token);

        return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
    }


    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUserHandler(@RequestParam("name") String q){

        List<User> users = userService.searchUser(q);
//        HashSet<User> set = new HashSet<>(users);



        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUserHandler(@RequestBody UpdateUserRequest req, @RequestHeader("Authorization") String token) throws UserException {

        User user = userService.findUserProfile(token);

        userService.updateUser(user.getId(), req);

        ApiResponse res = new ApiResponse("user updated successfully", true);

        return new ResponseEntity<ApiResponse>(res, HttpStatus.ACCEPTED);
    }


}
