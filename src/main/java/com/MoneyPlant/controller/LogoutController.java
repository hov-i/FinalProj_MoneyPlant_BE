package com.MoneyPlant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;



// 아직 미구현 상태입니다
@RestController
@RequestMapping("/v1/oauth")
public class LogoutController {

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletResponse response) {
        // Clear the authentication cookie
        final Cookie cookie = new Cookie("AUTH-TOKEN", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        // Optionally, you can also invalidate the session
        // session.invalidate();

        return ResponseEntity.ok().build();
    }
}
