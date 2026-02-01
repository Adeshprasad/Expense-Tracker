package com.adesh.expense_tracker.repository;

import com.adesh.expense_tracker.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {



}
