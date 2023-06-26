package com.MoneyPlant.service;

import com.MoneyPlant.entity.Expense;
import com.MoneyPlant.entity.Income;
import com.MoneyPlant.entity.User;
import com.MoneyPlant.repository.ExpenseRepository;
import com.MoneyPlant.repository.IncomeRepository;
import com.MoneyPlant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class LedgerService {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    // 수입, 지출 추가
    public boolean createIncome(int incomeAmount, String incomeDate, Long id) {
        Income income = new Income();
        income.setIncomeAmount(incomeAmount);
        income.setIncomeDate(incomeDate);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        income.setId(user);

        Income saveItem = incomeRepository.save(income);
        log.info("수입 : " + saveItem.getIncomeAmount());
        return true;
    }

    public boolean createExpense(int expenseAmount, String expenseDate, Long id) {
        Expense expense = new Expense();
        expense.setExpenseAmount(expenseAmount);
        expense.setExpenseDate(expenseDate);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        expense.setId(user);

        Expense saveItem = expenseRepository.save(expense);
        log.info("지출 : " + saveItem.getExpenseAmount());
        return true;
    }



    // 수입, 지출 수정
    // 수입 수정
    public boolean updateIncome(long incomeId, int newIncomeAmount, String newIncomeDate) {
        Optional<Income> optionalIncome = incomeRepository.findById(incomeId);
        if (optionalIncome.isPresent()) {
            Income income = optionalIncome.get();
            income.setIncomeAmount(newIncomeAmount);
            income.setIncomeDate(newIncomeDate);
            incomeRepository.save(income);
            return true;
        }
        return false;
    }

    // 지출 수정
    public boolean updateExpense(long expenseId, int newExpenseAmount, String newExpenseDate) {
        Optional<Expense> optionalExpense = expenseRepository.findById(expenseId);
        if (optionalExpense.isPresent()) {
            Expense expense = optionalExpense.get();
            expense.setExpenseAmount(newExpenseAmount);
            expense.setExpenseDate(newExpenseDate);
            expenseRepository.save(expense);
            return true;
        }
        return false;
    }


    // 수입, 지출 삭제


    // 합계
    // 수입, 지출 합계
    public int calculateTotalIncome() {
        List<Income> incomes = incomeRepository.findAll();
        int totalIncome = 0;
        for (Income income : incomes) {
            totalIncome += income.getIncomeAmount();
        }
        return totalIncome;
    }

    public int calculateTotalExpense() {
        List<Expense> expenses = expenseRepository.findAll();
        int totalExpense = 0;
        for (Expense expense : expenses) {
            totalExpense += expense.getExpenseAmount();
        }
        return totalExpense;
    }
}

