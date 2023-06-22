package com.MoneyPlant.service;

import com.MoneyPlant.dto.ExpenseDto;
import com.MoneyPlant.dto.IncomeDto;
import com.MoneyPlant.entity.Expense;
import com.MoneyPlant.entity.Income;
import com.MoneyPlant.repository.ExpenseRepository;
import com.MoneyPlant.repository.IncomeRepository;
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

    // 수입(Income)과 지출(Expense)의 추가, 수정, 삭제

    public IncomeDto createIncome(IncomeDto incomeDto) {
        // IncomeDto를 Income 엔티티로 변환
        Income income = new Income();
        income.setIncome(incomeDto.getIncome());
        income.setIncomeDate(incomeDto.getIncomeDate());

        // Income 엔티티를 저장
        Income savedIncome = incomeRepository.save(income);

        // 저장된 Income 엔티티를 IncomeDto로 변환하여 반환
        return mapIncomeToDto(savedIncome);
    }

    public ExpenseDto createExpense(ExpenseDto expenseDto) {
        // ExpenseDto를 Expense 엔티티로 변환
        Expense expense = new Expense();
        expense.setExpense(expenseDto.getExpense());
        expense.setExpenseDate(expenseDto.getExpenseDate());

        // Expense 엔티티를 저장
        Expense savedExpense = expenseRepository.save(expense);

        // 저장된 Expense 엔티티를 ExpenseDto로 변환하여 반환
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
}
