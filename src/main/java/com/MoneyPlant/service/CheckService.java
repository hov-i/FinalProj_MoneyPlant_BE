package com.MoneyPlant.service;

import com.MoneyPlant.dto.ExpenseDto;
import com.MoneyPlant.dto.IncomeDto;
import com.MoneyPlant.entity.*;
import com.MoneyPlant.repository.*;
import com.MoneyPlant.service.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CheckService {
    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;

    // 수입&카테고리 조회
    public List<IncomeDto> getIncomeWithCategory(UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        log.info("시용자 아이디 : " + userId);
        List<Income> incomeList = incomeRepository.findByUserId(userId);

        List<IncomeDto> incomeDtoList = new ArrayList<>();
        for (Income income : incomeList) {
            IncomeDto incomeDto = new IncomeDto();
            incomeDto.setIncomeId(income.getIncomeId());
            incomeDto.setIncomeAmount(income.getIncomeAmount());
            incomeDto.setIncomeDate(income.getIncomeDate());
            incomeDto.setIncomeContent(income.getIncomeContent());
            incomeDto.setCategoryId(income.getCategory().getCategoryId());
            incomeDto.setUserId(income.getUser().getId());

            String categoryName = categoryRepository.findByCategoryId(income.getCategory().getCategoryId()).getCategoryName();
            incomeDto.setCategoryName(categoryName);

            incomeDtoList.add(incomeDto);
        }

        return incomeDtoList;
    }


    // 지출&카테고리 조회
    public List<ExpenseDto> getExpenseWithCategory(UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        log.info("지출 사용자 아이디 : " + userId);
        List<Expense> expenseList = expenseRepository.findByUserId(userId);

        List<ExpenseDto> expenseDtoList = new ArrayList<>();
        for (Expense expense : expenseList) {
            ExpenseDto expenseDto = new ExpenseDto();
            expenseDto.setCategoryId(expense.getExpenseId());
            expenseDto.setExpenseAmount(expense.getExpenseAmount());
            expenseDto.setExpenseDate(expense.getExpenseDate());
            expenseDto.setExpenseContent(expense.getExpenseContent());
            expenseDto.setCategoryId(expense.getCategory().getCategoryId());
            expenseDto.setUserId(expense.getUser().getId());

            String categoryName = categoryRepository.findByCategoryId(expense.getCategory().getCategoryId()).getCategoryName();
            expenseDto.setCategoryName(categoryName);

            expenseDtoList.add(expenseDto);
        }

        return expenseDtoList;
    }

    //월간 지출 카테고리별 합계
    public Map<String, Double> getExpenseSumByCategory(UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        log.info("사용자 아이디: " + userId);

        List<Expense> expenseList = expenseRepository.findByUserId(userId);
        Map<String, Double> categoryExpenseMap = new HashMap<>();

        for (Expense expense : expenseList) {
            String categoryName = expense.getCategory().getCategoryName();
            double expenseAmount = expense.getExpenseAmount();

            categoryExpenseMap.put(categoryName, categoryExpenseMap.getOrDefault(categoryName, 0.0) + expenseAmount);
        }

        return categoryExpenseMap;
    }

}

