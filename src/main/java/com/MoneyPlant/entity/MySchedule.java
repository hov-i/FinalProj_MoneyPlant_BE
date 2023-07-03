package com.MoneyPlant.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="my_schedule")
@Setter
@Getter
@ToString
public class MySchedule { // 약어로 sc를 사용합니다.

    @Id
    @Column(name = "my_sc_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long myScId; // 일정 Id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user; // userId

    @Column(name = "my_sc_name", nullable = false)
    private String myScName; // 일정 이름

    @Column(name = "my_sc_color", nullable = false)
    private int myColor; // 일정 색

    @Column(name = "my_sc_budget")
    private int myScBudget; // 일정 예산

}
