package com.MoneyPlant.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkDto {

//    private Long workId;
    private Long userId; // join 으로 사용 예정
    private String workName;
    private String workColor;
    private String payType;
    private int workMoney;
    private String workDate;
    private int workTime;
    private int workPay;
    private int workTax;
    private String payDay;
}