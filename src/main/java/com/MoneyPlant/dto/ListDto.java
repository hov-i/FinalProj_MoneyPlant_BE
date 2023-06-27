package com.MoneyPlant.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListDto {
    private Long listId;
    private String content;
    private int amount;
    private String date;
    private Long userId;
}
