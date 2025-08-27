package com.yalcin.alptekin.onmuhasebebackendv2.income;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;

    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    public Income addIncome(Income income) {
        return incomeRepository.save(income);
    }

}
