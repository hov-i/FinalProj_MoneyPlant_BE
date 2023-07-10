package com.MoneyPlant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CalendarDto {
    private List<ScheduleDto> scheduleDtoList;
    private List<WorkDto> workDtoList;
    private List<ExpenseDto> dailyExpenseList;
    private List<IncomeDto> dailyincomeDtoList;

    public CalendarDto(List<ScheduleDto> scheduleDtoList, List<WorkDto> workDtoList) {
        this.scheduleDtoList = scheduleDtoList;
        this.workDtoList = workDtoList;
    }
}
