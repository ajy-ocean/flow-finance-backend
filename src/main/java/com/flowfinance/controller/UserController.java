package com.flowfinance.controller;

import com.flowfinance.entity.User;
import com.flowfinance.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user, HttpServletRequest request) {
        try {
            User savedUser = userService.register(user);
            HttpSession session = request.getSession(true);
            session.setAttribute("user", savedUser);
            return ResponseEntity.ok("Registered and logged in successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user, HttpServletRequest request) {
        try {
            User authUser = userService.findByUsernameAndPassword(user.getUsername(), user.getPassword());
            HttpSession session = request.getSession(true);
            session.setAttribute("user", authUser);
            return ResponseEntity.ok("Logged in successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok((User) session.getAttribute("user"));
    }
}