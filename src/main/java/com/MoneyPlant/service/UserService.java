package com.MoneyPlant.service;

import com.MoneyPlant.constant.ERole;
import com.MoneyPlant.dto.RoleDto;
import com.MoneyPlant.dto.UserInfoResponse;
import com.MoneyPlant.entity.Role;
import com.MoneyPlant.entity.User;
import com.MoneyPlant.repository.RoleRepository;
import com.MoneyPlant.repository.UserRepository;
import com.MoneyPlant.security.jwt.JwtUtils;
import com.MoneyPlant.service.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final RoleRepository roleRepository;

    @PostConstruct
    public void insertRoleData() {
        insertRole(ERole.ROLE_USER);
        insertRole(ERole.ROLE_ADMIN);

        System.out.println("Role 초기값 저장 완료");
    }

    private void insertRole(ERole roleName) {
        roleRepository.save(new Role(roleName));
    }

    public ResponseEntity<?> getUserInfo(UserDetailsImpl userDetails) {
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("");
        return ResponseEntity.ok()
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getName(),
                        userDetails.getEmail(),
                        role ));
    }
}
