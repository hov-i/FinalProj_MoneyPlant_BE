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
import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/calendar")
public class CalendarController {
    @Autowired
    private final ScheduleService scheduleService;
    private final WorkService workService;

    // 캘린더 정보 가져오기

    // 캘린더 일정 추가, 삭제, 수정 ( 구글 연동 되어있으면 즉시 구글 캘린더에도 적용시켜주기 (금액쓰는 것도 있음) )

    // 캘린더 가계부 추가, 삭제, 수정 ( )


// ===========================================================================
    // 캘린더 컨텐츠 전체 조회 (수입, 지출 추가 예정)
    @GetMapping("")
    public ResponseEntity<CalendarDto> CalendarView (@AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException {
        List<ScheduleDto> scheduleDtoList = scheduleService.getScheduleForCal(userDetails);
        List<WorkDto> workDtoList = workService.getWorkForCal(userDetails);


        CalendarDto calendarDto = new CalendarDto(scheduleDtoList, workDtoList);

        return ResponseEntity.ok(calendarDto);
    }
}
