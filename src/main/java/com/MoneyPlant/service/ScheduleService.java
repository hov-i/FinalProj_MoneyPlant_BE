package com.MoneyPlant.service;

import com.MoneyPlant.dto.ScheduleDto;
import com.MoneyPlant.entity.*;
import com.MoneyPlant.repository.*;
import com.MoneyPlant.service.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import javax.transaction.Transactional;
import java.util.List;

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
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    // 캘린더 일정 생성
    @Transactional
    public boolean createSchedule(ScheduleDto scheduleDto, UserDetailsImpl userDetails) {
        try {
            Long userId = userDetails.getId();
            scheduleDto.setUserId(userId);

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

            Schedule schedule = new Schedule();
            schedule.setUser(user);
            schedule.setCalId(scheduleDto.getCalId());
            schedule.setScName(scheduleDto.getScName());
            schedule.setScColor(scheduleDto.getScColor());
            schedule.setScDate(scheduleDto.getScDate());
            schedule.setScBudget(scheduleDto.getScBudget());

            scheduleRepository.save(schedule);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 나의 일정 가져와서 등록하기

    // 캘린더 일정 수정

    // 캘린더 일정 삭제


// ===========================================================================
    // 캘린더 전체 일정 조회 - 달력
    public List<ScheduleDto> getScheduleForCal(UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        List<Schedule> scheduleList = scheduleRepository.findByUserId(userId);

        List<ScheduleDto> scheduleDtoList = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
            ScheduleDto scheduleDto = new ScheduleDto();

            // 조회 내용 : 일정 날짜, 일정 이름, 일정 색깔
            scheduleDto.setScDate(schedule.getScDate());
            scheduleDto.setScName(schedule.getScName());
            scheduleDto.setScColor(schedule.getScColor());

            scheduleDtoList.add(scheduleDto);
        }
        return scheduleDtoList;
    }

    // 캘린더 전체 일정 조회 - 일별 상세
    public List<ScheduleDto> getScheduleForDetail(UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        List<Schedule> scheduleList = scheduleRepository.findByUserId(userId);

        List<ScheduleDto> scheduleDtoList = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
            ScheduleDto scheduleDto = new ScheduleDto();

            // 조회 내용 : 일정 날짜, 일정 이름, 일정 색깔, 일정 예산
            scheduleDto.setScDate(schedule.getScDate());
            scheduleDto.setScName(schedule.getScName());
            scheduleDto.setScColor(schedule.getScColor());
            scheduleDto.setScBudget(schedule.getScBudget());

            scheduleDtoList.add(scheduleDto);
        }
        return scheduleDtoList;
    }
}
