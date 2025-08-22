package com.yalcin.alptekin.onmuhasebe;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;

    public List<Income> getAllIncomes(){
        return incomeRepository.findAll();

    }

}
