package com.adesh.expense_tracker.controller;

import com.adesh.expense_tracker.dto.ExpenseRequest;
import com.adesh.expense_tracker.dto.ExpenseResponseDTO;
import com.adesh.expense_tracker.entity.Expense;
import com.adesh.expense_tracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {


    @GetMapping("/search")
    public ResponseEntity<Page<ExpenseResponseDTO>> searchExpenses(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Double minAmount,
            @RequestParam(required = false) Double maxAmount,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "expenseDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        return ResponseEntity.ok(
                expenseService.searchExpenses(
                        startDate, endDate, categoryId,
                        minAmount, maxAmount,
                        page, size, sortBy, direction
                )
        );
    }


    @GetMapping("/paged")
    public ResponseEntity<Page<ExpenseResponseDTO>> getExpensesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "expenseDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        return ResponseEntity.ok(
                expenseService.getExpensesPaginated(page, size, sortBy, direction)
        );
    }


    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // ✅ POST - create expense
    @PostMapping
    public ResponseEntity<Expense> addExpense(
            @Valid @RequestBody ExpenseRequest request) {
        return ResponseEntity.ok(expenseService.addExpense(request));
    }

    // ✅ GET - fetch all expenses
    @GetMapping
    public ResponseEntity<List<ExpenseResponseDTO>> getAllExpenses() {
        return ResponseEntity.ok(expenseService.getAllExpenses());
    }
}
