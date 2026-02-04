package com.adesh.expense_tracker.service;

import com.adesh.expense_tracker.dto.ExpenseRequest;
import com.adesh.expense_tracker.dto.ExpenseResponseDTO;
import com.adesh.expense_tracker.entity.Category;
import com.adesh.expense_tracker.entity.Expense;
import com.adesh.expense_tracker.exception.CategoryNotFoundException;
import com.adesh.expense_tracker.repository.CategoryRepository;
import com.adesh.expense_tracker.repository.ExpenseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;

    public ExpenseService(
            ExpenseRepository expenseRepository,
            CategoryRepository categoryRepository
    ) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
    }

    // ===============================
    // CREATE EXPENSE (DAY 7 + FIXED)
    // ===============================
    public Expense addExpense(ExpenseRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new CategoryNotFoundException("Category not found"));

        Expense expense = new Expense();
        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setExpenseDate(LocalDate.now()); // ✅ FIX IS HERE
        expense.setCategory(category);

        return expenseRepository.save(expense);
    }

    // ===============================
    // READ ALL
    // ===============================
    public List<ExpenseResponseDTO> getAllExpenses() {
        return expenseRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ===============================
    // PAGINATION & SORTING (DAY 8)
    // ===============================
    public Page<ExpenseResponseDTO> getExpensesPaginated(
            int page,
            int size,
            String sortBy,
            String direction
    ) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        return expenseRepository.findAll(pageRequest)
                .map(this::mapToDTO);
    }



    public Page<ExpenseResponseDTO> searchExpenses(
            LocalDate startDate,
            LocalDate endDate,
            Long categoryId,
            Double minAmount,
            Double maxAmount,
            int page,
            int size,
            String sortBy,
            String direction
    ) {

        Category category = null;
        if (categoryId != null) {
            category = categoryRepository.findById(categoryId)
                    .orElseThrow(() ->
                            new CategoryNotFoundException("Category not found"));
        }

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return expenseRepository
                .filterExpenses(startDate, endDate, category, minAmount, maxAmount, pageable)
                .map(this::mapToDTO);
    }



    public List<ExpenseResponseDTO> filterExpenses(
            LocalDate startDate,
            LocalDate endDate,
            Long categoryId,
            Double minAmount,
            Double maxAmount
    ) {

        Category category = null;

        if (categoryId != null) {
            category = categoryRepository.findById(categoryId)
                    .orElseThrow(() ->
                            new CategoryNotFoundException("Category not found"));
        }

        return expenseRepository
                .filterExpenses(startDate, endDate, category, minAmount, maxAmount)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ===============================
    // ANALYTICS (DAY 6)
    // ===============================
    public Map<String, Double> getCategoryAnalytics() {

        List<Object[]> results = expenseRepository.getCategoryWiseTotal();
        Map<String, Double> response = new HashMap<>();

        for (Object[] row : results) {
            String categoryName = (String) row[0];
            Double totalAmount = (Double) row[1];
            response.put(categoryName, totalAmount);
        }

        return response;
    }

    public Double getMonthlyAnalytics(int month, int year) {
        Double total = expenseRepository.getMonthlyTotal(month, year);
        return total != null ? total : 0.0;
    }

    // ===============================
    // ENTITY → DTO MAPPER
    // ===============================
    private ExpenseResponseDTO mapToDTO(Expense expense) {
        return new ExpenseResponseDTO(
                expense.getId(),
                expense.getTitle(),
                expense.getAmount(),
                expense.getExpenseDate(),
                expense.getCategory().getId(),
                expense.getCategory().getName()
        );
    }
}
