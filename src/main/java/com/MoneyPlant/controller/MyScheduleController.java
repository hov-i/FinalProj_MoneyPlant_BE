package com.MoneyPlant.controller;

import com.MoneyPlant.dto.MyScheduleDto;
import com.MoneyPlant.service.MyScheduleService;
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
@RequestMapping("/my-schedule")
@RequiredArgsConstructor
public class MyScheduleController {
    @Autowired
    private final MyScheduleService myScheduleService;

    // 마이페이지 나의 일정 등록
    @PostMapping("/create")
    public ResponseEntity<String> createMySchedule(
            @RequestBody List<MyScheduleDto> myScheduleList,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boolean allSuccess = true;
            for (MyScheduleDto myScheduleDto : myScheduleList) {
                boolean isSuccess = myScheduleService.createMySchedule(myScheduleDto, userDetails);
                if (!isSuccess) {
                    allSuccess = false;
                    break; // 반복 종료
                }
            }

        if (allSuccess) {
            return ResponseEntity.ok("나의 일정 생성 완료");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("일정 생성 실패");
        }
    }
}
