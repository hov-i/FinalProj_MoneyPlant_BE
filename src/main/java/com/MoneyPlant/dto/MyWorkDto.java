package com.MoneyPlant.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyWorkDto {
    private Long userId; // join 으로 사용 예정
    private String myWorkName;
    private String myWorkColor;
    private String myPayType;

    private int myWorkTime;
    private int myWorkPay;
    private int myPayDay;

}
