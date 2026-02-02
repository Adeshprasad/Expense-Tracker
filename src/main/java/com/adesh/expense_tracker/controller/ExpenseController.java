package com.adesh.expense_tracker.controller;

import com.adesh.expense_tracker.entity.Expense;
import com.adesh.expense_tracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    // POST - add expense
    @PostMapping
    public Expense addExpense(@RequestBody Expense expense) {
        return expenseService.addExpense(expense);
    }

    // GET - category summary
    @GetMapping("/summary/category")
    public Map<String, Double> categorySummary() {
        return expenseService.getCategorySummary();
    }

    // GET - monthly summary
    @GetMapping("/summary/monthly")
    public Double monthlySummary(
            @RequestParam int month,
            @RequestParam int year) {

        return expenseService.getMonthlySummary(month, year);
    }
}
