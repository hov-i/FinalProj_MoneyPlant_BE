package com.MoneyPlant.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="work")
@Setter
@Getter
@ToString
public class Work {
    @Id
    @Column(name = "work_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long workId; // 근무 Id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user; // userId

    @Column(name = "work_name", nullable = false)
    private String workName; // 근무 이름

    @Column(name = "work_color", nullable = false)
    private String workColor; // 근무 색

    @Column(name = "pay_type")
    private String payType; // 급여 타입 : 일급, 주급, 월급

    @Column(name = "work_money")
    private int workMoney; // 시급

    @Column(name = "work_date", nullable = false)
    private String workDate; // 근무 날짜 (다중 선택 ??)

    @Column(name = "work_time", nullable = false)
    private int workTime; // 근무 시간

    @Column(name = "work_pay")
    private int workPay; // 급여 (월급이나 일급일 때만, 직접 작성)

    @Column(name = "work_tax")
    private int workTax; // 급여 세금

    @Column(name = "pay_day", nullable = false)
    private String payDay; // 급여 지급일
}
