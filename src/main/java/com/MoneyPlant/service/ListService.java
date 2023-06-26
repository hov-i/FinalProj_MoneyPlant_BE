package com.MoneyPlant.service;

import com.MoneyPlant.dto.IncomeDto;
import com.MoneyPlant.entity.Income;
import com.MoneyPlant.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ListService {
    private final IncomeRepository incomeRepository;

//    public List<IncomeDto> getItemList() {
//        List<Income> incomes = incomeRepository.findAll();
//        List<IncomeDto> incomeDtos = new ArrayList<>();
//        for (Income income : incomes) {
//            IncomeDto incomeDto = new IncomeDto();
//            //incomeDto.setUserId(income.getUserId());
//            incomeDto.setIncomeAmount(income.getIncomeAmount());
//            incomeDto.setIncomeDate(income.getIncomeDate());
//            incomeDtos.add(incomeDto);
//        }
//        return incomeDtos;
//    }
}

