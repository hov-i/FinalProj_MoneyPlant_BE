package com.MoneyPlant.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/calendar")
public class CalendarController {
    // 캘린더 정보 가져오기

    // 캘린더 일정 추가, 삭제, 수정 ( 구글 연동 되어있으면 즉시 구글 캘린더에도 적용시켜주기 (금액쓰는 것도 있음) )

    // 캘린더 가계부 추가, 삭제, 수정 (  )
}
