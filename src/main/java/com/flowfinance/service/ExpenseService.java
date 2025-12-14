package com.flowfinance.service;

import com.flowfinance.entity.Expense;
import com.flowfinance.entity.User;
import com.flowfinance.repository.ExpenseRepository;
import com.flowfinance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    public Expense save(Expense expense, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        expense.setUser(user);
        return expenseRepository.save(expense);
    }

    public List<Expense> findAllByUserId(Long userId) {
        return expenseRepository.findByUserId(userId);
    }

    public Expense update(Long id, Expense expense, Long userId) {
        Expense existing = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        if (!existing.getUser().getId().equals(userId)) {
            throw new RuntimeException("Not authorized");
        }
        existing.setName(expense.getName());
        existing.setAmount(expense.getAmount());
        existing.setDate(expense.getDate());
        existing.setDescription(expense.getDescription());
        return expenseRepository.save(existing);
    }

    public void delete(Long id, Long userId) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        if (!expense.getUser().getId().equals(userId)) {
            throw new RuntimeException("Not authorized");
        }
        expenseRepository.delete(expense);
    }
}