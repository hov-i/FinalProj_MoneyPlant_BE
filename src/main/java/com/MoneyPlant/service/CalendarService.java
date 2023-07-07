package com.MoneyPlant.service;

import com.MoneyPlant.dto.*;
import com.MoneyPlant.entity.*;
import com.MoneyPlant.repository.*;
import com.MoneyPlant.service.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import javax.security.auth.kerberos.KerberosKey;
//import javax.servlet.http.HttpServletRequest;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CalendarService {
    private final ScheduleRepository scheduleRepository;
    private final WorkRepository workRepository;
    private final UserRepository userRepository;
    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryIncomeRepository categoryIncomeRepository;

    // 캘린더 일정 생성
    @Transactional
    public boolean createSchedule(ScheduleDto scheduleDto, UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();

        try {
            // 구글이 연동 되어 있는 경우 구글부터 진행한다
            scheduleDto.setUserId(userId);

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

            Schedule schedule = new Schedule();
            schedule.setUser(user);
            schedule.setCalId(scheduleDto.getCalId());
            schedule.setScName(scheduleDto.getScName());
            schedule.setColor(scheduleDto.getColor());
            schedule.setScDate(scheduleDto.getScDate());
            schedule.setScBudget(scheduleDto.getScBudget());

            scheduleRepository.save(schedule);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 마이페이지 나의 일정 가져와서 등록하기

    // 캘린더 일정 수정

    // 캘린더 일정 삭제


    // 캘린더 근무 생성
    public boolean createWork(WorkDto workDto, UserDetailsImpl userDetails) {
        try {
            Long userId = userDetails.getId();
            workDto.setUserId(userId);
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

            Work work = new Work();
            work.setUser(user);
            work.setWorkName(workDto.getWorkName());
            work.setColor(workDto.getColor());
            work.setWorkDate(workDto.getWorkDate());
            work.setWorkPay(workDto.getWorkPay());
            work.setWorkPayday(workDto.getWorkPayday());

            workRepository.save(work);

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    // 마이페이지 나의 근무 가져와서 등록하기


// ===========================================================================
    // 캘린더 전체 일정 조회 - 달력
    public List<ScheduleDto> getScheduleForCal(UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        List<Schedule> scheduleList = scheduleRepository.findByUserId(userId);

        List<ScheduleDto> scheduleDtoList = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
            ScheduleDto scheduleDto = new ScheduleDto();

            // 조회 내용 : 일정 날짜, 일정 이름, 일정 색
            scheduleDto.setScDate(schedule.getScDate());
            scheduleDto.setScName(schedule.getScName());
            scheduleDto.setColor(schedule.getColor());

            scheduleDtoList.add(scheduleDto);
        }
        return scheduleDtoList;
    }

    // 캘린더 전체 근무 조회 - 달력
    public List<WorkDto> getWorkForCal(UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        List<Work> workList = workRepository.findByUserId(userId);

        List<WorkDto> workDtoList = new ArrayList<>();
        for (Work work : workList) {
            WorkDto workDto = new WorkDto();

            // 조회 내용 :  근무 날짜, 근무 이름, 급여일, 근무 color, 급여
            work.setWorkName(workDto.getWorkName());
            work.setColor(workDto.getColor());
            work.setWorkDate(workDto.getWorkDate());
            work.setWorkPay(workDto.getWorkPay());
            work.setWorkPayday(workDto.getWorkPayday());

            workDtoList.add(workDto);
        }
        return workDtoList;
    }

    // 캘린더 전체 수입 합계 (daily Income) - 날짜, 수입 합계
    public Map<String, Integer> getDailyIncome(UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();

        List<Income> incomeList = incomeRepository.findByUserId(userId);

        Map<String, Integer> dailyIncomeList = new LinkedHashMap<>();

        for (Income income : incomeList) {
            String incomeDate = income.getIncomeDate();
            int incomeAmount = income.getIncomeAmount();

            // 이미 해당 날짜의 합계가 계산되었는지 확인
            if (dailyIncomeList.containsKey(incomeDate)) {
                int currentTotal = dailyIncomeList.get(incomeDate);
                dailyIncomeList.put(incomeDate, currentTotal + incomeAmount);
            } else {
                dailyIncomeList.put(incomeDate, incomeAmount);
            }
        }

        return dailyIncomeList;
    }

    // 캘린더 전체 지출 합계 (daily Expense) - 날짜, 지출 합계
    public Map<String, Integer> getDailyExpense(UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();

        List<Expense> expenseList = expenseRepository.findByUserId(userId);

        Map<String, Integer> dailyExpenseList = new LinkedHashMap<>();

        for (Expense expense : expenseList) {
            String expenseDate = expense.getExpenseDate();
            int expenseAmount = expense.getExpenseAmount();

            // 이미 해당 날짜의 합계가 계산되었는지 확인
            if (dailyExpenseList.containsKey(expenseDate)) {
                int currentTotal = dailyExpenseList.get(expenseDate);
                dailyExpenseList.put(expenseDate, currentTotal + expenseAmount);
            } else {
                dailyExpenseList.put(expenseDate, expenseAmount);
            }
        }

        return dailyExpenseList;
    }


    // 캘린더 전체 일정 조회 - 일별 상세
    public List<ScheduleDto> getScheduleForDetail(UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        List<Schedule> scheduleList = scheduleRepository.findByUserId(userId);

        List<ScheduleDto> scheduleDtoList = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
            ScheduleDto scheduleDto = new ScheduleDto();

            // 조회 내용 : 일정 날짜, 일정 이름, 일정 색, 일정 예산
            scheduleDto.setScDate(schedule.getScDate());
            scheduleDto.setScName(schedule.getScName());
            scheduleDto.setColor(schedule.getColor());
            scheduleDto.setScBudget(schedule.getScBudget());

            scheduleDtoList.add(scheduleDto);
        }
        return scheduleDtoList;
    }

    // 캘린더 전체 근무 조회 - 일별 상세
    public List<WorkDto> getWorkForDetail(UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        List<Work> workList = workRepository.findByUserId(userId);

        List<WorkDto> workDtoList = new ArrayList<>();
        for (Work work : workList) {
            WorkDto workDto = new WorkDto();

            // 조회 내용 :  근무 날짜, 근무 이름, 근무 시간(시작, 종료), 급여일, 근무 color, 급여
            workDto.setWorkName(work.getWorkName());
            workDto.setColor(work.getColor());
            workDto.setWorkDate(work.getWorkDate());
            workDto.setWorkStart(work.getWorkStart());
            workDto.setWorkEnd(work.getWorkEnd());
            workDto.setWorkDate(work.getWorkDate());
            workDto.setWorkPay(work.getWorkPay());

            workDtoList.add(workDto);
        }
        return workDtoList;
    }

    // 캘린더 전체 수입 detail (daily Income) - 날짜, 개별 수입 내역
    public List<IncomeDto> getIncomeWithCategory(UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        log.info("시용자 아이디 : " + userId);
        List<Income> incomeList = incomeRepository.findByUserId(userId);

        List<IncomeDto> incomeDtoList = new ArrayList<>();
        for (Income income : incomeList) {
            IncomeDto incomeDto = new IncomeDto();
            incomeDto.setIncomeId(income.getIncomeId());
            incomeDto.setIncomeAmount(income.getIncomeAmount());
            incomeDto.setIncomeDate(income.getIncomeDate());
            incomeDto.setIncomeContent(income.getIncomeContent());
            incomeDto.setCategoryIncomeId(income.getCategoryIncome().getCategoryIncomeId());
            incomeDto.setUserId(income.getUser().getId());

            String categoryIncomeName = categoryIncomeRepository.findByCategoryIncomeId(income.getCategoryIncome().getCategoryIncomeId()).getCategoryIncomeName();
            incomeDto.setCategoryIncomeName(categoryIncomeName);

            incomeDtoList.add(incomeDto);
        }

        return incomeDtoList;
    }

    // 캘린더 전체 지출 detail (daily Expense) - 날짜, 개별 지출 내역
    public List<ExpenseDto> getExpenseWithCategory(UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        log.info("지출 사용자 아이디 : " + userId);
        List<Expense> expenseList = expenseRepository.findByUserId(userId);

        List<ExpenseDto> expenseDtoList = new ArrayList<>();
        for (Expense expense : expenseList) {
            ExpenseDto expenseDto = new ExpenseDto();
            expenseDto.setExpenseId(expense.getExpenseId());
            expenseDto.setExpenseAmount(expense.getExpenseAmount());
            expenseDto.setExpenseDate(expense.getExpenseDate());
            expenseDto.setExpenseContent(expense.getExpenseContent());
            expenseDto.setCategoryId(expense.getCategory().getCategoryId());
            expenseDto.setUserId(expense.getUser().getId());

            String categoryName = categoryRepository.findByCategoryId(expense.getCategory().getCategoryId()).getCategoryName();
            expenseDto.setCategoryName(categoryName);

            expenseDtoList.add(expenseDto);
        }

        return expenseDtoList;
    }
}
