package com.MoneyPlant.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyWorkDto {
    private Long userId;
    private String myWkName; // 마이페이지 근무 이름
    private int myColor; // 마이페이지 근무 color
    private String myPayType; // 시급, 건별, 일급, 월급
    private int myWkMoney; // 시급 : 시급 금액, 건별 : 건별 금액 / 월급 : 연봉
    private double myWorkStart; // 마이페이지 근무 시작 시간
    private double myWorkEnd; // 마이페이지 근무 종료 시간
    private double myWorkRest; // 마이페이지 근무 휴식 시간
    private double myWkTax;
    private double myWkPay;
    private String myWkPayday;

}
