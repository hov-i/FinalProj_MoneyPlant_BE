package com.MoneyPlant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IncomeDto {
    private Long incomeId;
    private Long id;
    private int incomeAmount;
    private String incomeDate;
}
