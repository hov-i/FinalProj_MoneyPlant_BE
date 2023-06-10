package com.MoneyPlant.controller;

import com.MoneyPlant.dto.AccountDto;
import com.MoneyPlant.service.AccountService;
import com.MoneyPlant.user.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/v1/oauth")
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/user/info")
    public ResponseEntity getUserInfo(Principal principal) {
        Account account = accountService.getAccount(Long.valueOf(principal.getName()));
        return ResponseEntity.ok().body(AccountDto.convertToDto(account));
    }
}
