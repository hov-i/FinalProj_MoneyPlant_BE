package com.MoneyPlant.controller;

import com.MoneyPlant.dto.WorkDto;
import com.MoneyPlant.entity.Work;
import com.MoneyPlant.service.WorkService;
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
@RequestMapping("/work")
@RequiredArgsConstructor
public class WorkController {
    @Autowired
    WorkService workService;

    @PostMapping
    public ResponseEntity<String> createWork(
            @RequestBody WorkDto workDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boolean isSuccess = workService.createWork(workDto, userDetails);

        if (isSuccess) {
            return ResponseEntity.ok("근무가 생성되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("근무 생성을 실패했습니다.");
        }
    }
}
