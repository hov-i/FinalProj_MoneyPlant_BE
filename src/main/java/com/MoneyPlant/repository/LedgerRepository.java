package com.MoneyPlant.repository;

import com.MoneyPlant.entity.Expense;
import com.MoneyPlant.entity.Income;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LedgerRepository {
    Income saveIncome(Income income);
    Income findIncomeById(Long incomeId);
    Income updateIncome(Income income);
    void deleteIncome(Income income);
    List<Income> findAllIncomes();

    Expense saveExpense(Expense expense);
    Expense findExpenseById(Long expenseId);
    Expense updateExpense(Expense expense);
    void deleteExpense(Expense expense);
    List<Expense> findAllExpense();
}
