package com.flowfinance.service;

import com.flowfinance.entity.User;
import com.flowfinance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User register(User user) {
//        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
//            throw new RuntimeException("Username exists");
//        }
        return userRepository.save(user);
    }

    public User findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(u -> u.getPassword().equals(password))
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }
}