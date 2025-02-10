package com.vikramaditya.journalApp.service;

import com.vikramaditya.journalApp.entity.User;
import com.vikramaditya.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user){
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(ObjectId id) {
        return userRepository.findById(id);
    }

    public Optional<User> deleteUserById(ObjectId id) {
        Optional<User> entry = userRepository.findById(id);
        entry.ifPresent(userRepository::delete);
        return entry;
    }

    public Optional<User> updateUserById(ObjectId id, User user) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setUserName(user.getUserName());
            existingUser.setPassword(user.getPassword());
            return userRepository.save(user);
        });
    }


}
