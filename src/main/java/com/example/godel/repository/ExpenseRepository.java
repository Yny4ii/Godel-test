package com.example.godel.repository;

import com.example.godel.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByLocalDateBetweenAndCategory_Name(LocalDate first, LocalDate after, String name);

    List<Expense> findAllByCategory_Name(String name);

    List<Expense> findAllByLocalDateBetween(LocalDate from, LocalDate until);
}
