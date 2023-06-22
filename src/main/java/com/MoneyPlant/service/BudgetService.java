package com.MoneyPlant.service;

import com.MoneyPlant.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class BudgetService {
    @Autowired
    BudgetRepository budgetRepository;


}
