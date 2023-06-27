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
            work.setPayType(workDto.getPayType());
            work.setWorkName(workDto.getWorkName());
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

    // 캘린더 근무 수정

    // 캘린더 근무 삭제

}
