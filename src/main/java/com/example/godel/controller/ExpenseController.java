package com.example.godel.controller;

import com.example.godel.entity.Expense;
import com.example.godel.service.CategoryService;
import com.example.godel.service.ExpenseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ExpenseController {

    private final ExpenseService expenseService;
    private final CategoryService categoryService;

    public ExpenseController(ExpenseService expenseService, CategoryService categoryService) {
        this.expenseService = expenseService;
        this.categoryService = categoryService;
    }

    @GetMapping("/expense")
    public String findAll(Model model) {
        model.addAttribute("expenses", expenseService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("price", expenseService.getExpenses());
        model.addAttribute("expensiveMonth", expenseService.getMostExpenseMonth());
        return "expense";
    }



    @GetMapping("/createForm")
    public String createFrom(Model model) {
        Expense expense = new Expense();
        model.addAttribute("expense", expense);
        model.addAttribute("categories", categoryService.findAll());
        return "newexpense";
    }

    @PostMapping("/saveExpense")
    public String saveExpense(@ModelAttribute("expense") Expense expense) {
        expenseService.saveExpense(expense);
        return "redirect:/expense";
    }


    @GetMapping("/find")
    public String findByDate(@RequestParam(name = "category", required = false) String category,
                             @RequestParam(name = "dateFirst", required = false) String dateFrom,
                             @RequestParam(name = "dateSecond", required = false) String dateUntil, Model model) {
        model.addAttribute("expenses", expenseService.getResults(dateFrom, dateUntil, category));
        return "expense";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable("id") Long id, Model model) {
        Expense expense = expenseService.findById(id);
        model.addAttribute("expense", expense);
        return "update_expense";
    }


    @GetMapping("/deleteById/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        expenseService.deleteById(id);
        return "redirect:/expense";

    }
}
