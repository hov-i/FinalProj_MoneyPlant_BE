package com.MoneyPlant.controller;

import com.MoneyPlant.dto.*;
import com.MoneyPlant.service.*;
import com.MoneyPlant.service.jwt.UserDetailsImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "https://localhost:3000", allowedHeaders = "*")
@RequestMapping("/calendar")
public class CalendarController {
    @Autowired
    private final CalendarService calendarService;
    private final LedgerService ledgerService;
    private final CheckService checkService;


    // 캘린더 일정 추가, 삭제, 수정 ( 구글 연동 되어있으면 즉시 구글 캘린더에도 적용시켜주기 (금액쓰는 것도 있음) )
    @GetMapping("/get/schedule")
    public ResponseEntity<List<ScheduleDto>> getSchedule(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<ScheduleDto> scheduleDtos = calendarService.getScheduleForCal(userDetails);
        return ResponseEntity.ok(scheduleDtos);
    }
    // 캘린더 일정 등록

    @PostMapping("/create/schedule")
    public ResponseEntity<String> createSchedule(
            @RequestBody ScheduleDto scheduleDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boolean isSuccess = calendarService.createSchedule(scheduleDto, userDetails);
        if (isSuccess) {
            return ResponseEntity.ok("일정 생성 완료");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("일정 생성 실패");
        }
    }

    @PostMapping("/update/schedule")
    public ResponseEntity<String> updateSchedule(
            @RequestBody ScheduleDto scheduleDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boolean isSuccess = calendarService.updateSchedule(scheduleDto, userDetails);
        if (isSuccess) {
            return ResponseEntity.ok("일정 수정 완료");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("일정 수정 실패");
        }
    }
    @DeleteMapping("/delete/schedule")
    public ResponseEntity<String> deleteSchedule(
            @RequestBody Long scId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boolean isSuccess = calendarService.deleteSchedule(scId, userDetails);
        if (isSuccess) {
            return ResponseEntity.ok("일정 삭제 완료");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("일정 삭제 실패");
        }
    }

    // 캘린더 근무 등록
    @PostMapping("/create/work")
    public ResponseEntity<String> createWork(
            @RequestBody List<WorkDto> workDtoList,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boolean allSuccess = true;

        for (WorkDto workDto : workDtoList) {
            boolean isSuccess = calendarService.createWork(workDto, userDetails);

            if(!isSuccess) {
                allSuccess = false;
                break;
            }
        }

        if (allSuccess) {
            return ResponseEntity.ok("근무 생성 완료");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("근무 생성을 실패");
        }
    }
    // 캘린더 가계부 추가, 삭제, 수정 ( )


// ===========================================================================
    // 캘린더 컨텐츠 전체 조회 (수입, 지출 추가 예정)
    @GetMapping("")
    public ResponseEntity<CalendarDto> getCalendarView (@AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException {
        List<ScheduleDto> scheduleDtoList = calendarService.getScheduleForCal(userDetails);
        List<WorkDto> workDtoList = calendarService.getWorkForCal(userDetails);
        Map<String, Integer> dailyExpenseList =  ledgerService.getDailyExpense(userDetails);
        Map<String, Integer> dailyIncomeList =  ledgerService.getDailyIncome(userDetails);

        CalendarDto calendarDto = new CalendarDto(scheduleDtoList, workDtoList, dailyExpenseList, dailyIncomeList);

        return ResponseEntity.ok(calendarDto);
    }

// 서비스 calender 맞는지 확인 필요
//    // 캘린더 전체 일정 조회 - 일별 상세
//    @GetMapping("/detail")
//    public List<ScheduleDto> getScheduleForDetail(@AuthenticationPr incipal UserDetailsImpl userDetails) {
//        return calendarService.getScheduleForDetail(userDetails);
//    }
//
//    // 캘린더 전체 근무 조회 - 일별 상세
//    @GetMapping("/detail")
//    public List<WorkDto> getWorkForDetail(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return calendarService.getWorkForDetail(userDetails);
//    }

    // 캘린더 전체 수입 detail (daily Income) - 날짜, 개별 수입 내역
    @GetMapping("/income/category")
    public ResponseEntity<List<IncomeDto>> getIncomeWithCategory(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<IncomeDto> incomeDtoList = checkService.getIncomeWithCategory(userDetails);
        return ResponseEntity.ok(incomeDtoList);
    }
    // 캘린더 전체 지출 detail (daily Expense) - 날짜, 개별 지출 내역
    @GetMapping("/expense/category")
    public ResponseEntity<List<ExpenseDto>> getExpenseWithCategory(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<ExpenseDto> expenseDtoList = checkService.getExpenseWithCategory(userDetails);
        return ResponseEntity.ok(expenseDtoList);
    }

}
