package com.yalcin.alptekin.onmuhasebebackendv2.income;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/income")
public class IncomeController {

    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @GetMapping
    public List<Income> getIncomes() {
        return incomeService.getAllIncomes();
    }

    @PostMapping
    public Income addIncome(@RequestBody Income income) {
        return incomeService.addIncome(income);
    }
}
