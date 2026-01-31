package com.adesh.expense_tracker.controller;

import com.adesh.expense_tracker.model.Expense;
import com.adesh.expense_tracker.model.User;
import com.adesh.expense_tracker.repository.ExpenseRepository;
import com.adesh.expense_tracker.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseController(ExpenseRepository expenseRepository,
                             UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public Expense createExpense(@RequestBody ExpenseRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Expense expense = new Expense();
        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDate(request.getDate());
        expense.setUser(user);

        return expenseRepository.save(expense);
    }
}
