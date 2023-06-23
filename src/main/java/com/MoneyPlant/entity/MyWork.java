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

    @ManyToOne
    @JoinColumn(name = "id")
    private User user; // userId

    @Column(name = "my_pay_type")
    private String payType; // 급여 타입

    @Column(name = "my_work_name", nullable = false)
    private String workName; // 근무 이름

    @Column(name = "my_work_time", nullable = false)
    private int workTime; // 근무 시간

    @Column(name = "my_work_pay")
    private int workPay; // 일일 급여

    @Column(name = "my_pay_day", nullable = false)
    private int payDay; // 급여 지급일

}
