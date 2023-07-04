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
    @Column(name = "my_wk_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long myWkId; // 마이페이지 근무 Id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user; // userId

    @Column(name = "my_wk_name", nullable = false)
    private String myWkName; // 근무 이름

    @Column(name = "my_color", nullable = false)
    private int myColor; // 근무 color

    @Column(name = "my_wk_pay")
    private double myWkPay; // 급여 ( money * tax * date)

    @Column(name = "my_wk_payday", nullable = false)
    private String myWkPayday; // 급여 지급일
}
