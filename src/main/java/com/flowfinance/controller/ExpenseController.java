package com.flowfinance.controller;

import com.flowfinance.entity.Expense;
import com.flowfinance.entity.User;
import com.flowfinance.service.ExpenseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    private User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            throw new RuntimeException("Unauthorized");
        }
        return (User) session.getAttribute("user");
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody Expense expense, HttpServletRequest request) {
        try {
            User user = getCurrentUser(request);
            expenseService.save(expense, user.getId());
            return ResponseEntity.ok("Expense added");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }

    @GetMapping
    public ResponseEntity<List<Expense>> list(HttpServletRequest request) {
        try {
            User user = getCurrentUser(request);
            return ResponseEntity.ok(expenseService.findAllByUserId(user.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Expense expense, HttpServletRequest request) {
        try {
            User user = getCurrentUser(request);
            expenseService.update(id, expense, user.getId());
            return ResponseEntity.ok("Expense updated");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id, HttpServletRequest request) {
        try {
            User user = getCurrentUser(request);
            expenseService.delete(id, user.getId());
            return ResponseEntity.ok("Expense deleted");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }
}