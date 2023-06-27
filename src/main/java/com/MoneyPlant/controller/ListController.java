package com.MoneyPlant.controller;


import com.MoneyPlant.dto.ExpenseDto;
import com.MoneyPlant.dto.IncomeDto;
import com.MoneyPlant.service.LedgerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "https://localhost:3000", allowedHeaders = "*")
@RequestMapping("/list")
public class ListController {

    private final LedgerService ledgerService;

}



