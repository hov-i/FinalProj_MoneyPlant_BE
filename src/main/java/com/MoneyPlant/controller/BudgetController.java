package com.MoneyPlant.controller;

import com.MoneyPlant.dto.BudgetDto;
import com.MoneyPlant.entity.Budget;
import com.MoneyPlant.service.BudgetService;
import com.MoneyPlant.service.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/mybudget")
public class BudgetController {
    @Autowired
    BudgetService budgetService;

    @PostMapping
    public ResponseEntity<String> createBudget(
            @RequestBody BudgetDto budgetDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boolean isSuccess = budgetService.createBudgetForCategories(budgetDto, userDetails);

        if (isSuccess) {
            return ResponseEntity.ok("예산을 성공적으로 생성했습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예산 생성에 실패했습니다.");
        }
    }
}

