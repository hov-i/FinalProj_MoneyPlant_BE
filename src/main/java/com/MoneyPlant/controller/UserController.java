package com.MoneyPlant.controller;

import com.MoneyPlant.dto.MessageResponse;
import com.MoneyPlant.service.AuthService;
import com.MoneyPlant.service.UserService;
import com.MoneyPlant.service.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "https://localhost:3000", allowedHeaders = "*")
//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("userDetails : " + userDetails);
        System.out.println("userDetails.getName :" + userDetails.getName());
        System.out.println("userDetails.getId : " + userDetails.getId());
        System.out.println("userDetails.getEmail : " + userDetails.getEmail());
        return userService.getUserInfo(userDetails);
    }

//    @PutMapping("/{email}/password")
//    public ResponseEntity<?> updatePassword(@PathVariable("email") String email, @RequestBody String newPassword) {
//        ResponseEntity<?> response;
//
//        try {
//            response = authService.updatePassword(email, newPassword);
//        } catch (Exception e) {
//            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("비밀번호 업데이트 실패: " + e.getMessage()));
//        }
//
//        return response;
//    }
}
