package com.MoneyPlant.service;

import com.MoneyPlant.dto.MyWorkDto;
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
public class MyWorkService {
    private final MyWorkRepository myWorkRepository;
    private final UserRepository userRepository;

    // 마이페이지 나의 근무 생성
    public boolean createMyWork(MyWorkDto myWorkDto, UserDetailsImpl userDetails) {
        try {
            Long userId = userDetails.getId();
            myWorkDto.setUserId(userId);

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

            MyWork myWork = new MyWork();
            myWork.setUser(user);
            myWork.setPayType(myWorkDto.getMyPayType());
            myWork.setWorkName(myWorkDto.getMyWorkName());
            myWork.setWorkTime( myWorkDto.getMyWorkTime());
            myWork.setWorkPay( myWorkDto.getMyWorkPay());
            myWork.setPayDay( myWorkDto.getMyPayDay());

            myWorkRepository.save(myWork);
            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    // 마이페이지 나의 근무 수정

    // 마이페이지 나의 근무 삭제

}
