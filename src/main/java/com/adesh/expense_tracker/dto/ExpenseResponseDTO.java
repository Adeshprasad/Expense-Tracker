package com.adesh.expense_tracker.dto;

import java.time.LocalDate;

public class ExpenseResponseDTO {

    private Long id;
    private String title;
    private Double amount;
    private LocalDate expenseDate;
    private Long categoryId;
    private String categoryName;

    public ExpenseResponseDTO(
            Long id,
            String title,
            Double amount,
            LocalDate expenseDate,
            Long categoryId,
            String categoryName
    ) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
