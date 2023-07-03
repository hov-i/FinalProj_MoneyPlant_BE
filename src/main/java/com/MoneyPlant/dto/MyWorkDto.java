package com.MoneyPlant.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyWorkDto {
    private Long userId; // join 으로 사용 예정
    private String myWorkName;
    private int myColor;
    private String myPayType;
    private int myWorkMoney;
    private double myWorkTime;
    private double myWorkTax;
    private double myWorkPay;
    private String myPayDay;

}
