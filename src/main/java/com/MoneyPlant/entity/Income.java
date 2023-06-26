package com.MoneyPlant.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "income")
@Getter @Setter
@ToString
public class Income {
    @Id
    @Column(name = "income_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long incomeId; // 수입 Id

    @ManyToOne
    @JoinColumn(name = "id")
    private User id;

    @Column(name = "income_amount")
    private int incomeAmount;

    @Column(name = "income_date")
    private String incomeDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

}
