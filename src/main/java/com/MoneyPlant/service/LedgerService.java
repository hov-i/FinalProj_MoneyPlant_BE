package com.MoneyPlant.service;

import com.MoneyPlant.dto.BudgetDto;
import com.MoneyPlant.dto.ExpenseDto;
import com.MoneyPlant.dto.IncomeDto;
import com.MoneyPlant.entity.*;
import com.MoneyPlant.repository.*;
import com.MoneyPlant.service.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class LedgerService {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    //등록
    //----------------------------------------------------------
    // 수입 등록
    public boolean createIncome(IncomeDto incomeDto, UserDetailsImpl userDetails) {
        try {
            Long userId = userDetails.getId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

            Income income = new Income();
            income.setUser(user);
            income.setIncomeAmount(incomeDto.getIncomeAmount());
            income.setIncomeDate(incomeDto.getIncomeDate());

            incomeRepository.save(income);
            return true;
        } catch (Exception e) {
            log.error("수입 등록 error", e);
            return false;
        }
    }

    // 지출 등록
    public boolean createExpense(ExpenseDto expenseDto, UserDetailsImpl userDetails) {
        try {
            Long userId = userDetails.getId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

            Expense expense = new Expense();
            expense.setUser(user);
            expense.setExpenseAmount(expenseDto.getExpenseAmount());
            expense.setExpenseDate(expenseDto.getExpenseDate());

            expenseRepository.save(expense);
            return true;
        } catch (Exception e) {
            log.error("지출 등록 error", e);
            return false;
        }
    }

    //----------------------------------------------------------
    //수정
    // 수입 수정
    public boolean updateIncome(Long incomeId, IncomeDto incomeDto) {
        try {
            Income income = incomeRepository.findById(incomeId)
                    .orElseThrow(() -> new RuntimeException("해당 수입을 찾을 수 없습니다."));

            // 수정할 내용을 incomeDto에서 가져와서 해당 수입에 반영합니다.
            income.setIncomeAmount(incomeDto.getIncomeAmount());
            income.setIncomeDate(incomeDto.getIncomeDate());

            incomeRepository.save(income); // 수정된 수입을 저장합니다.
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    //----------------------------------------------------------
    //조회
    // 수입 조회
    public List<IncomeDto> getIncomes(UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        List<Income> incomeList = incomeRepository.findByUserId(userId);

        List<IncomeDto> incomeDtoList = new ArrayList<>();
        for (Income income : incomeList) {
            IncomeDto incomeDto = new IncomeDto();
            //필요 조회 정보
            incomeDto.setIncomeAmount(income.getIncomeAmount());
            incomeDto.setIncomeDate(income.getIncomeDate());

            incomeDtoList.add(incomeDto);
        }

        return incomeDtoList;
    }

    // 지출 조회
    public List<ExpenseDto> getExpenses(UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        List<Expense> expenseList = expenseRepository.findByUserId(userId);

        List<ExpenseDto> expenseDtoList = new ArrayList<>();
        for (Expense expense : expenseList) {
            ExpenseDto expenseDto = new ExpenseDto();
            expenseDto.setExpenseAmount(expense.getExpenseAmount());
            expenseDto.setExpenseDate(expense.getExpenseDate());

            expenseDtoList.add(expenseDto);
        }

        return expenseDtoList;
    }





}

