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
    private Long scId; // 일정 Id

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

    @Column(name = "my_sc_name", nullable = false)
    private String workName; // 일정 이름

    @Column(name = "my_sc_budget")
    private String scBudget; // 일정 예산

}
