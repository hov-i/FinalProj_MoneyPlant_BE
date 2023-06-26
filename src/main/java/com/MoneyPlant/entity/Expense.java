package com.MoneyPlant.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "expense")
@Getter @Setter
@ToString
public class Expense {
    @Id
    @Column(name = "expense_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ExpenseId; // 지출 Id

    @ManyToOne
    @JoinColumn(name = "id")
    private User id;

    @Column(name= "expense_amount")
    private int expenseAmount;

    @Column(name = "expense_date")
    private String expenseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
