package com.MoneyPlant.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "list")
@Getter @Setter
@ToString
//카테고리 아이디 외래키로 참조
public class List {
    @Id
    @Column(name = "list_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listId;

    @Column
    private String content;

    @Column
    private int amount;

    @Column
    private String date;
}
