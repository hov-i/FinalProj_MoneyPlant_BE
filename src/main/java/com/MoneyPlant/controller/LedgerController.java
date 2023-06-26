package com.MoneyPlant.controller;

import com.MoneyPlant.service.LedgerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "https://localhost:3000", allowedHeaders = "*")
@RequestMapping("/api/ledger")
public class LedgerController {

    private final LedgerService ledgerService;

    // 내역 저장
    @PostMapping("/income")
    public ResponseEntity<Boolean> incomeCreate(@RequestBody Map<String, Object> data) {
        int incomeAmount = (Integer) data.get("incomeAmount");
        String incomeDate = (String) data.get("incomeDate");
        Long userId = (Long) data.get("userId");
        boolean result = ledgerService.createIncome(incomeAmount, incomeDate, userId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/expense")
    public ResponseEntity<Boolean> expenseCreate(@RequestBody Map<String, Object> data) {
        int expenseAmount = (Integer) data.get("expenseAmount");
        String expenseDate = (String) data.get("expenseDate");
        Long userId = (Long) data.get("userId");
        boolean result = ledgerService.createExpense(expenseAmount, expenseDate, userId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    // 수입, 지출 수정
    @PutMapping("/{type}/{id}")
    public ResponseEntity<Boolean> updateEntry(
            @PathVariable String type,
            @PathVariable long id,
            @RequestBody Map<String, Object> data) {
        if ("income".equalsIgnoreCase(type)) {
            int newIncomeAmount = (int) data.get("incomeAmount");
            String newIncomeDate = (String) data.get("incomeDate");
            boolean result = ledgerService.updateIncome(id, newIncomeAmount, newIncomeDate);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else if ("expense".equalsIgnoreCase(type)) {
            int newExpenseAmount = (int) data.get("expenseAmount");
            String newExpenseDate = (String) data.get("expenseDate");
            boolean result = ledgerService.updateExpense(id, newExpenseAmount, newExpenseDate);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    // 합계
    @GetMapping("/total")
    public ResponseEntity<Map<String, Integer>> calculateTotals() {
        int totalIncome = ledgerService.calculateTotalIncome();
        int totalExpense = ledgerService.calculateTotalExpense();

        Map<String, Integer> totals = new HashMap<>();
        totals.put("totalIncome", totalIncome);
        totals.put("totalExpense", totalExpense);

        return ResponseEntity.ok(totals);
    }

}

