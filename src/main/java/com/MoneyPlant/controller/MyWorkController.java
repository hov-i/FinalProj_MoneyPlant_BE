package com.MoneyPlant.controller;

import com.MoneyPlant.dto.MyWorkDto;
import com.MoneyPlant.service.MyWorkService;
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
@RequestMapping("/my-work")
@RequiredArgsConstructor
public class MyWorkController {
    @Autowired
    private final MyWorkService myWorkService;

    // 마이페이지 나의 근무 등록
    @PostMapping("/create")
    public ResponseEntity<String> createMyWork (
            @RequestBody List<MyWorkDto> myWorkList,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boolean allSuccess = true;

        for (MyWorkDto myWorkDto : myWorkList) {
            boolean isSuccess = myWorkService.createMyWork(myWorkDto, userDetails);

            if(!isSuccess) {
                allSuccess = false;
                break;
            }
        }
        if (allSuccess) {
            return ResponseEntity.ok("나의 근무 생성 완료");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("근무 생성을 실패");
        }
    }
}

