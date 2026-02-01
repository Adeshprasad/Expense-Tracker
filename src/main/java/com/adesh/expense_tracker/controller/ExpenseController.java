package com.adesh.expense_tracker.controller;

import com.adesh.expense_tracker.dto.ExpenseRequest;
import com.adesh.expense_tracker.entity.Category;
import com.adesh.expense_tracker.entity.Expense;
import com.adesh.expense_tracker.repository.CategoryRepository;
import com.adesh.expense_tracker.repository.ExpenseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;

    public ExpenseController(
            ExpenseRepository expenseRepository,
            CategoryRepository categoryRepository
    ) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
    }

    // POST /expenses
    @PostMapping
    public Expense createExpense(@RequestBody ExpenseRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Expense expense = new Expense();
        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setCategory(category);

        return expenseRepository.save(expense);
    }

    public ExpenseRepository getExpenseRepository() {
        return expenseRepository;
    }

    // GET /expenses
    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }
}
