package com.MoneyPlant.controller;

import com.MoneyPlant.dto.ExpenseDto;
import com.MoneyPlant.dto.IncomeDto;
import com.MoneyPlant.service.LedgerService;
import com.MoneyPlant.service.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "https://localhost:3000", allowedHeaders = "*")
@RequestMapping("/ledger")
public class LedgerController {

    @Autowired
    private final LedgerService ledgerService;

    //등록
    // 수입 등록
    @PostMapping("/income")
    public ResponseEntity<String> createIncome(
            @RequestBody List<IncomeDto> incomeDtoList,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boolean allSuccess = true;

        for (IncomeDto incomeDto : incomeDtoList) {
            boolean isSuccess = ledgerService.createIncome(incomeDto, userDetails);

            if (!isSuccess) {
                allSuccess = false;
                break;
            }
        }

        if (allSuccess) {
            return ResponseEntity.ok("수입 등록 완료");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("실패");
        }
    }

    // 지출 등록
    @PostMapping("/expense")
    public ResponseEntity<String> createExpense(
            @RequestBody List<ExpenseDto> expenseDtoList,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boolean allSuccess = true;

        for (ExpenseDto expenseDto : expenseDtoList) {
            boolean isSuccess = ledgerService.createExpense(expenseDto, userDetails);

            if (!isSuccess) {
                allSuccess = false;
                break;
            }
        }

        if (allSuccess) {
            return ResponseEntity.ok("지출 등록 완료");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("실패");
        }
    }

    //수정
    // 수입 수정
    @PutMapping("/{incomeId}")
    public ResponseEntity<String> updateIncome(
            @PathVariable Long incomeId,
            @RequestBody IncomeDto incomeDto) {

        boolean isSuccess = ledgerService.updateIncome(incomeId, incomeDto);

        if (isSuccess) {
            return ResponseEntity.ok("수입이 성공적으로 수정되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("수입 수정에 실패했습니다.");
        }
    }


    //조회
    // 수입 조회
    @GetMapping("/incomes")
    public ResponseEntity<List<IncomeDto>> getIncomes(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<IncomeDto> incomeDtoList = ledgerService.getIncomes(userDetails);
        return ResponseEntity.ok(incomeDtoList);
    }

    // 지출 조회
    @GetMapping("/expenses")
    public ResponseEntity<List<ExpenseDto>> getExpenses(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<ExpenseDto> expenseDtoList = ledgerService.getExpenses(userDetails);
        return ResponseEntity.ok(expenseDtoList);
    }


}

