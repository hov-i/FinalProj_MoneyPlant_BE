package com.MoneyPlant.service;

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
    private final CategoryRepository categoryRepository;

    //등록
    //----------------------------------------------------------
    // 수입 등록
    public boolean createIncome(IncomeDto incomeDto, UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        incomeDto.setUserId(userId);

        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
            Long categoryId = Long.valueOf(incomeDto.getCategoryId());
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다."));

            Income income = new Income();
            income.setUser(user);
            income.setCategory(category);
            income.setIncomeAmount(incomeDto.getIncomeAmount());
            income.setIncomeDate(incomeDto.getIncomeDate());
            income.setIncomeContent(incomeDto.getIncomeContent());

            incomeRepository.save(income);
            return true;
        } catch (Exception e) {
            System.err.println("수입 등록 실패: " + e.getMessage());
            return false;
        }
    }

    // 지출 등록
    public boolean createExpense(ExpenseDto expenseDto, UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        expenseDto.setUserId(userId);

        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
            Long categoryId = Long.valueOf(expenseDto.getCategoryId());
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다."));

            Expense expense = new Expense();
            expense.setUser(user);
            expense.setCategory(category);
            expense.setExpenseAmount(expenseDto.getExpenseAmount());
            expense.setExpenseDate(expenseDto.getExpenseDate());
            expense.setExpenseContent(expenseDto.getExpenseContent());

            expenseRepository.save(expense);
            return true;
        } catch (Exception e) {
            System.err.println("지출 등록 실패: " + e.getMessage());
            return false;
        }
    }


    //----------------------------------------------------------
    //수정
    // 수입 수정
    public boolean updateIncome(Long incomeId, IncomeDto updatedIncomeDto) {
        try {
            Income income = incomeRepository.findById(incomeId)
                    .orElseThrow(() -> new RuntimeException("수입 정보를 찾을 수 없습니다."));

            User user = userRepository.findById(updatedIncomeDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

            Category category = categoryRepository.findById(updatedIncomeDto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다."));

            // 수정할 필드 업데이트
            income.setUser(user);
            income.setCategory(category);
            income.setIncomeAmount(updatedIncomeDto.getIncomeAmount());
            income.setIncomeDate(updatedIncomeDto.getIncomeDate());
            income.setIncomeContent(updatedIncomeDto.getIncomeContent());

            incomeRepository.save(income); // 수정된 수입 정보 저장
            return true;
        } catch (Exception e) {
            System.err.println("수입 수정 실패: " + e.getMessage());
            return false;
        }
    }

    // 지출 수정
    public boolean updateExpense(Long expenseId, ExpenseDto updatedExpenseDto) {
        try {
            Expense expense = expenseRepository.findById(expenseId)
                    .orElseThrow(() -> new RuntimeException("지출 정보를 찾을 수 없습니다."));

            User user = userRepository.findById(updatedExpenseDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

            Category category = categoryRepository.findById(updatedExpenseDto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다."));

            // 수정할 필드 업데이트
            expense.setUser(user);
            expense.setCategory(category);
            expense.setExpenseAmount(updatedExpenseDto.getExpenseAmount());
            expense.setExpenseDate(updatedExpenseDto.getExpenseDate());
            expense.setExpenseContent(updatedExpenseDto.getExpenseContent());

            expenseRepository.save(expense); // 수정된 지출 정보 저장
            return true;
        } catch (Exception e) {
            System.err.println("지출 수정 실패: " + e.getMessage());
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
            //필요 조회 정보
            expenseDto.setExpenseAmount(expense.getExpenseAmount());
            expenseDto.setExpenseDate(expense.getExpenseDate());

            expenseDtoList.add(expenseDto);
        }

        return expenseDtoList;
    }

    // 일간 개별 합계 조회
    // 월간 개별 합계 조회





}

