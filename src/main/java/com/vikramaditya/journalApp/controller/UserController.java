package com.vikramaditya.journalApp.controller;


import com.vikramaditya.journalApp.entity.JournalEntry;
import com.vikramaditya.journalApp.entity.User;
import com.vikramaditya.journalApp.service.JournalEntryService;
import com.vikramaditya.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);

    }


    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {

            if (user.getDate() == null) {
                user.setDate(LocalDateTime.now());
            }

            userService.saveUser(user);

            // Return HTTP 201 Created with a success message
            return new ResponseEntity<>("User created successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle any errors during entry saving
            return new ResponseEntity<>("Failed to create user.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("id/{myId}")
    public User GetUserById(@PathVariable ObjectId myId) {
        return userService.getUserById(myId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + myId));
    }


    @DeleteMapping("id/{myId}")
    public User DeleteUserById(@PathVariable ObjectId myId){
        return userService.deleteUserById(myId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + myId));

    }

    @PutMapping("id/{myId}")
    public User UpdateUserById(@PathVariable ObjectId myId, @RequestBody User user){
        return userService.updateUserById(myId, user)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + myId));

    }


}
