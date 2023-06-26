package com.MoneyPlant.controller;

import com.MoneyPlant.dto.ExpenseDto;
import com.MoneyPlant.dto.IncomeDto;
import com.MoneyPlant.service.LedgerService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ledger")
public class LedgerController {

    private final LedgerService ledgerService;

    public LedgerController(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }


    // 내역 저장
        @PostMapping("/income")
        public ResponseEntity<IncomeDto> createIncome(@RequestBody IncomeDto incomeDto) {
            IncomeDto createdIncome = ledgerService.createIncome(incomeDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdIncome);
        }

        @PostMapping("/expense")
        public ResponseEntity<ExpenseDto> createExpense(@RequestBody ExpenseDto expenseDto) {
            ExpenseDto createdExpense = ledgerService.createExpense(expenseDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdExpense);
        }


        // 수입, 지출 수정
        @PutMapping("/income/{incomeId}")
        public ResponseEntity<IncomeDto> updateIncome(@PathVariable Long incomeId, @RequestBody IncomeDto incomeDto) throws ChangeSetPersister.NotFoundException {
            IncomeDto updatedIncome = ledgerService.updateIncome(incomeId, incomeDto);
            return ResponseEntity.ok(updatedIncome);
        }

    @PutMapping("/expense/{expenseId}")
    public ResponseEntity<ExpenseDto> updateExpense(@PathVariable Long expenseId, @RequestBody ExpenseDto expenseDto) throws ChangeSetPersister.NotFoundException {
        ExpenseDto updatedExpense = ledgerService.updateExpense(expenseId, expenseDto);
        return ResponseEntity.ok(updatedExpense);
    }

    // 합계
    @PostMapping("/total")
    public ResponseEntity<Integer> getTotal(@RequestBody List<IncomeDto> incomeList,
                                            @RequestBody List<ExpenseDto> expenseList) {
        int totalIncome = calculateTotalIncome(incomeList);
        int totalExpense = calculateTotalExpense(expenseList);
        int balance = totalIncome - totalExpense;
        return ResponseEntity.ok(balance);
    }

    private int calculateTotalIncome(List<IncomeDto> incomeList) {
        int totalIncome = 0;
        for (IncomeDto income : incomeList) {
            totalIncome += income.getIncome();
        }
        return totalIncome;
    }

    private int calculateTotalExpense(List<ExpenseDto> expenseList) {
        int totalExpense = 0;
        for (ExpenseDto expense : expenseList) {
            totalExpense += expense.getExpense();
        }
        return totalExpense;
    }




}

