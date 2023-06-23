package com.MoneyPlant.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="schedule")
@Setter
@Getter
@ToString
public class Schedule { // sc을 약어로 사용합니다.
    @Id
    @Column(name = "sc_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long scId; // 일정 Id

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

    @Column(name = "calendar_id", unique = true)
    private String calId; // 구글 캘린더 ID

    @Column(name = "sc_name", nullable = false)
    private String workName; // 일정 이름

    @Column(name = "sc_date", nullable = false)
    private String payType; // 일정 날짜

    @Column(name = "sc_budget")
    private String scBudget; // 일정 예산

}
