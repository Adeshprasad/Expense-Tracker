package com.adesh.expense_tracker.repository;
import java.util.List;

import com.adesh.expense_tracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {



    @Query("""
SELECT e.category.name, SUM(e.amount)
FROM Expense e
GROUP BY e.category.name
""")
    List<Object[]> getTotalByCategory();


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


}
