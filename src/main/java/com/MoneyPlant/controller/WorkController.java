package com.MoneyPlant.controller;

import com.MoneyPlant.dto.WorkDto;
import com.MoneyPlant.service.WorkService;
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
@RequestMapping("/work")
@RequiredArgsConstructor
public class WorkController {
    @Autowired
    private final WorkService workService;

    // 캘린더 근무 등록
    @PostMapping("/create")
    public ResponseEntity<String> createWork(
            @RequestBody List<WorkDto> workDtoList,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boolean allSuccess = true;

        for (WorkDto workDto : workDtoList) {
            boolean isSuccess = workService.createWork(workDto, userDetails);

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
}
