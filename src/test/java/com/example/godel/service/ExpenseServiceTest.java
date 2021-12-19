package com.example.godel.service;

import com.example.godel.entity.Category;
import com.example.godel.entity.Expense;
import com.example.godel.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ExpenseServiceTest {
    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    ExpenseService expenseService;

    @Test
    void save() {
        Expense expense = new Expense("test", LocalDate.now(), 12.3);

        Category category = new Category();
        expense.setCategory(category);

        expenseRepository.save(expense);
        assertThat(expenseRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    void deleteById() {
        Expense expense = new Expense("test", LocalDate.now(), 12.3);

        expenseRepository.save(expense);
        assertThat(expenseRepository.findAll().size()).isEqualTo(1);
        expenseRepository.deleteById(1L);
        assertThat(expenseRepository.findAll().size()).isEqualTo(0);

    }

    @Test
    void findAllByCategory_Name() {
        Expense expense1 = new Expense();
        Expense expense2 = new Expense();
        Expense expense3 = new Expense();

        Category category = new Category();
        category.setName("test");

        expense1.setCategory(category);
        expense2.setCategory(category);

        expenseRepository.saveAll(List.of(expense1, expense2, expense3));

        List<Expense> expensesFromDb = expenseRepository.findAllByCategory_Name("test");
        assertThat(expensesFromDb.size()).isEqualTo(2);
    }

    @Test
    void findAllByLocalDateBetween() {
        LocalDate localDate1 = LocalDate.of(2021, 11, 10);
        LocalDate localDate2 = LocalDate.of(2021, 11, 20);

        Expense expense1 = new Expense();
        expense1.setLocalDate(localDate1);

        Expense expense2 = new Expense();
        expense2.setLocalDate(localDate1);

        Expense expense3 = new Expense();
        expense3.setLocalDate(localDate2);

        expenseRepository.saveAll(List.of(expense1, expense2, expense3));

        LocalDate dateFrom = LocalDate.of(2021, 11, 9);
        LocalDate dateUntil = LocalDate.of(2021, 11, 19);

        assertThat(expenseRepository.findAllByLocalDateBetween(dateFrom, dateUntil).size()).isEqualTo(2);
    }

    @Test
    void findAllByLocalDateBetweenAndCategory_Name() {
        LocalDate localDate1 = LocalDate.of(2021, 11, 10);

        Category category1 = new Category();
        category1.setName("category1");

        Category category2 = new Category();
        category2.setName("category2");

        Expense expense1 = new Expense();
        expense1.setLocalDate(localDate1);
        expense1.setCategory(category1);

        Expense expense2 = new Expense();
        expense2.setLocalDate(localDate1);
        expense2.setCategory(category1);

        Expense expense3 = new Expense();
        expense3.setLocalDate(localDate1);
        expense3.setCategory(category2);

        expenseRepository.saveAll(List.of(expense1, expense2, expense3));

        LocalDate dateFrom = LocalDate.of(2021, 11, 9);
        LocalDate dateUntil = LocalDate.of(2021, 11, 19);

        assertThat(expenseRepository.findAllByLocalDateBetweenAndCategory_Name
                        (dateFrom, dateUntil, "category1")
                .size())
                .isEqualTo(2);
    }

    @Test
    void getExpenses() {
        Expense expense1 = new Expense();
        Expense expense2 = new Expense();

        expense1.setAmount(1.2);
        expense2.setAmount(1.2);

        expenseRepository.saveAll(List.of(expense1, expense2));

        assertThat(expenseService.getExpenses()).isEqualTo(2.4);
    }
}