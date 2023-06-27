package com.MoneyPlant.service;

import com.MoneyPlant.dto.WorkDto;
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
public class WorkService {
    private final WorkRepository workRepository;
    private final UserRepository userRepository;

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
            work.setWorkColor(work.getWorkColor());
            work.setPayType(workDto.getPayType());
            work.setWorkDate(workDto.getWorkDate());
            work.setWorkTime(workDto.getWorkTime());
            work.setWorkPay(workDto.getWorkPay());
            work.setPayDay(workDto.getPayDay());

            workRepository.save(work);

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    // 나의 근무 가져와서 등록하기

    // 캘린더 근무 수정

    // 캘린더 근무 삭제


// ===========================================================================
    // 캘린더 전체 근무 조회 - 달력
    public List<WorkDto> getWorkForCal(UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        List<Work> workList = workRepository.findByUserId(userId);

        List<WorkDto> workDtoList = new ArrayList<>();
        for (Work work : workList) {
            WorkDto workDto = new WorkDto();

            // 조회 내용 :  근무 날짜, 근무 이름, 근무 색깔
            workDto.setWorkDate(work.getWorkDate());
            workDto.setWorkName(work.getWorkName());
            workDto.setWorkColor(work.getWorkColor());

            workDtoList.add(workDto);
        }
        return workDtoList;
    }

    // 캘린더 전체 근무 조회 - 일별 상세
    public List<WorkDto> getWorkForDetail(UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        List<Work> workList = workRepository.findByUserId(userId);

        List<WorkDto> workDtoList = new ArrayList<>();
        for (Work work : workList) {
            WorkDto workDto = new WorkDto();

            // 조회 내용 :  근무 날짜, 근무 이름, 근무 색깔
            workDto.setWorkName(work.getWorkName());
            workDto.setWorkColor(work.getWorkColor());
            workDto.setWorkDate(work.getWorkDate());
            workDto.setWorkTime(work.getWorkTime());
            workDto.setWorkPay(work.getWorkPay());

            workDtoList.add(workDto);
        }
        return workDtoList;
    }
}
