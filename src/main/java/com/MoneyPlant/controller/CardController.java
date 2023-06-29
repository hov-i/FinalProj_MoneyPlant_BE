package com.MoneyPlant.controller;

import com.MoneyPlant.service.CardService;
import com.MoneyPlant.service.jwt.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://localhost:3000", allowedHeaders = "*")
@RequestMapping("/cardrecommend")
public class CardController {
    @Autowired
    CardService cardService;

    // 카드 추천
    @GetMapping("")
    public ResponseEntity<List<String>> getTop3List(@AuthenticationPrincipal UserDetailsImpl userDetails){
    List<String> getTop3 = cardService.manyExpenseTop3Category(userDetails);
    return ResponseEntity.ok(getTop3);
    }
}
