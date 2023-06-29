package com.MoneyPlant.controller;

import com.MoneyPlant.dto.ScheduleDto;
import com.MoneyPlant.service.ScheduleService;
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
@Slf4j
@CrossOrigin(origins = "https://localhost:3000", allowedHeaders = "*")
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    @Autowired
    private final ScheduleService scheduleService;

    // 캘린더 일정 등록
    @PostMapping("/create")
    public ResponseEntity<String> createSchedule(
            @RequestBody List<ScheduleDto> scheduleList,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boolean allSuccess = true;

        for (ScheduleDto scheduleDto : scheduleList) {
            boolean isSuccess = scheduleService.createSchedule(scheduleDto, userDetails);

            if (!isSuccess) {
                allSuccess = false;
                break; // 반복 종료
            }
        }

        if (allSuccess) {
            return ResponseEntity.ok("일정 생성 완료");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("일정 생성 실패");
        }
    }
}