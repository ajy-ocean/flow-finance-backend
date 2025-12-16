package com.flowfinance.controller;

import com.flowfinance.entity.Expense;
import com.flowfinance.entity.User;
import com.flowfinance.service.ExpenseService;
import com.flowfinance.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;
    
    @Autowired
    private UserService userService; 

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userService.findByUsername(username); 
        }
        throw new RuntimeException("Unauthorized: User principal not found");
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody Expense expense) {
        try {
            User user = getCurrentUser();
            expenseService.save(expense, user.getId());
            return ResponseEntity.ok("Expense added");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adding expense: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Expense>> list() {
        try {
            User user = getCurrentUser();
            return ResponseEntity.ok(expenseService.findAllByUserId(user.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Expense expense) {
        try {
            User user = getCurrentUser();
            expenseService.update(id, expense, user.getId());
            return ResponseEntity.ok("Expense updated");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating expense: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            User user = getCurrentUser();
            expenseService.delete(id, user.getId());
            return ResponseEntity.ok("Expense deleted");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting expense: " + e.getMessage());
        }
    }
}