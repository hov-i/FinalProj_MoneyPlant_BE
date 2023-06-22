package com.MoneyPlant.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ExpenseDto {
    private Long expenseId;
    private int expense;
    private String expenseDate;
}
