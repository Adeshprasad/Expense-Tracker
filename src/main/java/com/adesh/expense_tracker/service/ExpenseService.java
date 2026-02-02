package com.adesh.expense_tracker.service;

import com.adesh.expense_tracker.entity.Expense;
import com.adesh.expense_tracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    // -------------------------------
    // 1️⃣ Add new expense (POST)
    // -------------------------------
    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    // -----------------------------------------
    // 2️⃣ Category-wise expense summary
    // -----------------------------------------
    public Map<String, Double> getCategorySummary() {

        List<Object[]> results = expenseRepository.getTotalByCategory();
        Map<String, Double> summary = new HashMap<>();

        for (Object[] row : results) {
            String categoryName = (String) row[0];
            Double totalAmount = (Double) row[1];
            summary.put(categoryName, totalAmount);
        }

        return summary;
    }

    // -----------------------------------------
    // 3️⃣ Monthly expense summary
    // -----------------------------------------
    public Double getMonthlySummary(int month, int year) {

        Double total = expenseRepository.getMonthlyTotal(month, year);
        return total != null ? total : 0.0;
    }
}
