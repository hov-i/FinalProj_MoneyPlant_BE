package com.MoneyPlant.controller;

import com.MoneyPlant.dto.ScheduleDto;
import com.MoneyPlant.entity.Schedule;
import com.MoneyPlant.service.ScheduleService;
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
@Slf4j
@CrossOrigin(origins = "https://localhost:3000", allowedHeaders = "*")
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<String> createSchedule(
            @RequestBody ScheduleDto scheduleDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boolean isSuccess = scheduleService.createSchedule(scheduleDto, userDetails);

        if (isSuccess) {
            return ResponseEntity.ok("일정이 생성되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("일정 생성을 실패했습니다.");
        }
    }



}