package com.MoneyPlant.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="budget")
@Setter
@Getter
@ToString
public class Budget {
    @Id
    @Column(name = "budget_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long budgetId; // 예산 Id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user; // userId

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category; //카테고리 Id

    @Column(name = "budget_money", nullable = false)
    private int budgetMoney; //예산 돈

    @Column(name = "budget_month", nullable = false)
    private LocalDateTime budgetMonth; // 예산 달

}
