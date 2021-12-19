package com.example.godel.service;

import com.example.godel.entity.Expense;
import com.example.godel.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public void saveExpense(Expense expense) {
        expenseRepository.save(expense);
    }

    public List<Expense> findAll() {
        return expenseRepository.findAll();
    }

    public List<Expense> findByCategoryName(String name) {
        return expenseRepository.findAllByCategory_Name(name);
    }

    public List<Expense> getResults(String from, String until, String category) {
        LocalDate dateFrom = createFrom(from);
        LocalDate dateUntil = createUntil(until);
        if (category.equals("")) {
            return expenseRepository.findAllByLocalDateBetween(dateFrom, dateUntil);
        } else {
            return expenseRepository.findAllByLocalDateBetweenAndCategory_Name(dateFrom, dateUntil, category);
        }
    }

    public Expense findById(Long id) {
        return expenseRepository.findById(id).orElseThrow();
    }

    private LocalDate createUntil(String date) {
        LocalDate localDate;
        if (date.isEmpty() || date == null) {
            localDate = LocalDate.now();
        } else {
            localDate = LocalDate.parse(date);
        }
        return localDate;
    }

    private LocalDate createFrom(String date) {
        LocalDate localDate;
        if (date.isEmpty() || date == null) {
            localDate = LocalDate.parse("1970-01-01");
        } else {
            localDate = LocalDate.parse(date);
        }
        return localDate;
    }

    public Double getExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    public void deleteById(Long id) {
        expenseRepository.deleteById(id);
    }

    public String getMostExpenseMonth() {
        List<Expense> expenses = expenseRepository.findAll();
        if (expenses.isEmpty()) {
            return "Empty";
        }
        expenses.sort(Comparator.comparing(Expense::getAmount));
        LocalDate mostExpenseMonth = expenses.get(0).getLocalDate();
        double expenseSum = expenses.get(0).getAmount();
        double sumOfExpenseMonth = expenses.get(0).getAmount();

        for (int i = 1; i < expenses.size(); i++) {
            if (expenses.get(i - 1).getLocalDate().getMonth() == expenses.get(i).getLocalDate().getMonth()) {
                sumOfExpenseMonth += expenses.get(i).getAmount();
            } else {
                sumOfExpenseMonth = expenses.get(i).getAmount();
            }

            if (sumOfExpenseMonth > expenseSum) {
                expenseSum = sumOfExpenseMonth;
                mostExpenseMonth = expenses.get(i).getLocalDate();

            }
        }
        return (mostExpenseMonth.format(DateTimeFormatter.ofPattern("yyyy-MM")) + " === " + sumOfExpenseMonth);
    }
}
