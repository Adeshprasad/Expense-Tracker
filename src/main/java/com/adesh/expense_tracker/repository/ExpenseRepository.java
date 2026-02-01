package com.adesh.expense_tracker.repository;

import com.adesh.expense_tracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import com.adesh.expense_tracker.repository.ExpenseRepository;
import com.adesh.expense_tracker.repository.CategoryRepository;


public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
