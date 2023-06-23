package com.MoneyPlant.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyWorkDto {
    private Long userId; // join 으로 사용 예정
    private String myPayType;
    private String myWorkName;
    private int myWorkTime;
    private int myWorkPay;
    private int myPayDay;

}
