package com.yalcin.alptekin.onmuhasebebackendv2.expense;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    private List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @PostMapping
    private Expense addExpense(@RequestBody Expense expense) {
        return expenseService.addExpense(expense);
    }
}