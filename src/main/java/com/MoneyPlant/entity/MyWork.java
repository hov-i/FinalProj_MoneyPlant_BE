package com.MoneyPlant.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="my_work")
@Setter
@Getter
@ToString
public class MyWork {
    @Id
    @Column(name = "my_work_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long myWorkId; // 마이페이지 근무 Id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user; // userId

    @Column(name = "my_work_name", nullable = false)
    private String myWorkName; // 근무 이름

    @Column(name = "my_work_color", nullable = false)
    private int myWorkColor; // 근무 색

    @Column(name = "my_pay_type", nullable = false)
    private String myPayType; // 급여 타입 : 건별, 주급, 월급

    @Column(name = "my_work_money")
    private int myWorkMoney; // 시급, 일급

    @Column(name = "my_work_time")
    private int myWorkTime; // 근무 시간

    @Column(name = "my_work_pay")
    private int myWorkPay; // 급여 (월급이나 일급일 때만, 직접 작성)

    @Column(name = "my_work_tax")
    private double myWorkTax; // 급여 세금

    @Column(name = "my_pay_day", nullable = false)
    private String myPayDay; // 급여 지급일
}
