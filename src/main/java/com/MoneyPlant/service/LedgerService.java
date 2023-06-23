package com.MoneyPlant.service;

import com.MoneyPlant.dto.ExpenseDto;
import com.MoneyPlant.dto.IncomeDto;
import com.MoneyPlant.entity.Expense;
import com.MoneyPlant.entity.Income;
import com.MoneyPlant.repository.ExpenseRepository;
import com.MoneyPlant.repository.IncomeRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LedgerService {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;

    public LedgerService(IncomeRepository incomeRepository, ExpenseRepository expenseRepository) {
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
    }




    // 수입, 지출 추가
    public IncomeDto createIncome(IncomeDto incomeDto) {
        // IncomeDto 를 Income entity 로 변환
        Income income = new Income();
        income.setIncome(incomeDto.getIncome());
        income.setIncomeDate(incomeDto.getIncomeDate());

        // Income entity 를 저장
        Income savedIncome = incomeRepository.save(income);

        // 저장된 Income entity 를 IncomeDto 로 변환하여 반환
        return mapIncomeToDto(savedIncome);
    }

    public ExpenseDto createExpense(ExpenseDto expenseDto) {
        // ExpenseDto 를 Expense entity 로 변환
        Expense expense = new Expense();
        expense.setExpense(expenseDto.getExpense());
        expense.setExpenseDate(expenseDto.getExpenseDate());

        // Expense entity 를 저장
        Expense savedExpense = expenseRepository.save(expense);

        // 저장된 Expense entity 를 ExpenseDto 로 변환하여 반환
        return mapExpenseToDto(savedExpense);
    }

    private IncomeDto mapIncomeToDto(Income income) {
        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setIncomeId(income.getIncomeId());
        incomeDto.setIncome(income.getIncome());
        incomeDto.setIncomeDate(income.getIncomeDate());
        return incomeDto;
    }

    private ExpenseDto mapExpenseToDto(Expense expense) {
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setExpenseId(expense.getExpenseId());
        expenseDto.setExpense(expense.getExpense());
        expenseDto.setExpenseDate(expense.getExpenseDate());
        return expenseDto;
    }


    // 수입, 지출 수정
    public IncomeDto updateIncome(Long incomeId, IncomeDto incomeDto) throws ChangeSetPersister.NotFoundException {
        // 수입 entity 조회
        Income income = incomeRepository.findById(incomeId)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        // 수입 정보 수정
        income.setIncome(incomeDto.getIncome());
        income.setIncomeDate(incomeDto.getIncomeDate());

        // 수정된 수입 entity 저장
        Income updatedIncome = incomeRepository.save(income);

        // 수정된 수입 entity IncomeDto 로 변환하여 반환
        return mapIncomeToDto(updatedIncome);
    }

    public ExpenseDto updateExpense(Long expenseId, ExpenseDto expenseDto) throws ChangeSetPersister.NotFoundException {
        // 지출 entity 조회
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        // 지출 정보 수정
        expense.setExpense(expenseDto.getExpense());
        expense.setExpenseDate(expenseDto.getExpenseDate());

        // 수정된 지출 entity 저장
        Expense updatedExpense = expenseRepository.save(expense);

        // 수정된 지출 entity ExpenseDto 로 변환하여 반환
        return mapExpenseToDto(updatedExpense);
    }


    // 수입, 지출 삭제
//    public IncomeDto deleteIncome(IncomeDto incomeDto){
//
//    }


    // 합계
    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public int calculateTotalIncome() {
        List<Income> incomes = incomeRepository.findAll();
        return incomes.stream()
                .mapToInt(Income::getIncome)
                .sum();
    }

    public int calculateTotalExpense() {
        List<Expense> expenses = expenseRepository.findAll();
        return expenses.stream()
                .mapToInt(Expense::getExpense)
                .sum();
    }
}
