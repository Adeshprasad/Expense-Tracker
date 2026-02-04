package com.adesh.expense_tracker.repository;

import com.adesh.expense_tracker.entity.Category;
import com.adesh.expense_tracker.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // ===============================
    // DYNAMIC FILTER QUERY (DAY 6 STEP 2)
    // ===============================
    @Query("""
        SELECT e FROM Expense e
        WHERE (:startDate IS NULL OR e.expenseDate >= :startDate)
          AND (:endDate IS NULL OR e.expenseDate <= :endDate)
          AND (:category IS NULL OR e.category = :category)
          AND (:minAmount IS NULL OR e.amount >= :minAmount)
          AND (:maxAmount IS NULL OR e.amount <= :maxAmount)
    """)
    List<Expense> filterExpenses(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("category") Category category,
            @Param("minAmount") Double minAmount,
            @Param("maxAmount") Double maxAmount
    );

    // ===============================
    // ANALYTICS (DAY 6 STEP 3)
    // ===============================
    @Query("""
        SELECT e.category.name, SUM(e.amount)
        FROM Expense e
        GROUP BY e.category.name
    """)
    List<Object[]> getCategoryWiseTotal();

    @Query("""
        SELECT SUM(e.amount)
        FROM Expense e
        WHERE MONTH(e.expenseDate) = :month
          AND YEAR(e.expenseDate) = :year
    """)
    Double getMonthlyTotal(
            @Param("month") int month,
            @Param("year") int year
    );


    @Query("""
    SELECT e FROM Expense e
    WHERE (:startDate IS NULL OR e.expenseDate >= :startDate)
      AND (:endDate IS NULL OR e.expenseDate <= :endDate)
      AND (:category IS NULL OR e.category = :category)
      AND (:minAmount IS NULL OR e.amount >= :minAmount)
      AND (:maxAmount IS NULL OR e.amount <= :maxAmount)
""")
    Page<Expense> filterExpenses(
            LocalDate startDate,
            LocalDate endDate,
            Category category,
            Double minAmount,
            Double maxAmount,
            Pageable pageable
    );

}
