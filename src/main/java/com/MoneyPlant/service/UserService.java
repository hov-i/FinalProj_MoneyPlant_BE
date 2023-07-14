package com.MoneyPlant.service;

import com.MoneyPlant.constant.ERole;
import com.MoneyPlant.dto.MessageResponse;
import com.MoneyPlant.dto.UserInfoResponse;
import com.MoneyPlant.entity.Role;
import com.MoneyPlant.entity.User;
import com.MoneyPlant.repository.RoleRepository;
import com.MoneyPlant.repository.UserRepository;
import com.MoneyPlant.service.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

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

//// 비밀번호 업데이트
//public ResponseEntity<?> updatePassword(String email, String newPassword) {
//    Optional<User> optionalUser = userRepository.findByEmail(email);
//    if (optionalUser.isEmpty()) {
//        return ResponseEntity.badRequest().body(new MessageResponse("유저 확인 불가"));
//    }
//
//    User user = optionalUser.get();
//
//    // 새로운 비밀번호 암호화
//    String encodedPassword = encoder.encode(newPassword);
//    user.setPassword(encodedPassword);
//    userRepository.save(user);
//
//    return ResponseEntity.ok(new MessageResponse("변경 성공"));
//}
}
