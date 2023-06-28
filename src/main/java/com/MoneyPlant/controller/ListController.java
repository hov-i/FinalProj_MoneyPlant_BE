package com.MoneyPlant.controller;


import com.MoneyPlant.dto.ExpenseDto;
import com.MoneyPlant.dto.IncomeDto;
import com.MoneyPlant.service.LedgerService;
import com.MoneyPlant.service.ListService;
import com.MoneyPlant.service.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "https://localhost:3000", allowedHeaders = "*")
@RequestMapping("/list")
public class ListController {

    @Autowired
    private final ListService listService;

    // 수입&카테고리 조회
    @GetMapping("/income/category")
    public ResponseEntity<List<IncomeDto>> getIncomeWithCategory(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<IncomeDto> incomeDtoList = listService.getIncomeWithCategory(userDetails);
        return ResponseEntity.ok(incomeDtoList);
    }

    // 지출&카테고리 조회
    @GetMapping("/expense/category")
    public ResponseEntity<List<ExpenseDto>> getExpenseWithCategory(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<ExpenseDto> expenseDtoList = listService.getExpenseWithCategory(userDetails);
        return ResponseEntity.ok(expenseDtoList);
    }

}



