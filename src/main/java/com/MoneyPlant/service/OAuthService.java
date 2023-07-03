package com.MoneyPlant.service;

import com.MoneyPlant.config.GoogleOAuth;
import com.MoneyPlant.constant.ERole;
import com.MoneyPlant.dto.GetGoogleOAuthRes;
import com.MoneyPlant.dto.GoogleOAuthToken;
import com.MoneyPlant.dto.GoogleUser;
import com.MoneyPlant.dto.UserInfoResponse;
import com.MoneyPlant.entity.RefreshToken;
import com.MoneyPlant.entity.Role;
import com.MoneyPlant.entity.User;
import com.MoneyPlant.repository.RoleRepository;
import com.MoneyPlant.repository.UserRepository;
import com.MoneyPlant.security.jwt.JwtUtils;
import com.MoneyPlant.service.jwt.RefreshTokenService;
import com.MoneyPlant.service.jwt.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class OAuthService {

    private final GoogleOAuth socialOAuth;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;

    public String request() throws IOException {
        String redirectURL = socialOAuth.getOAuthRedirectURL();
        return redirectURL;
    }

    public ResponseEntity<?> OAuthLogin(String code) throws JsonProcessingException {
        ResponseEntity<String> accessToken = socialOAuth.requestAccessToken(code);
        GoogleOAuthToken googleOAuthToken = socialOAuth.getAccessToken(accessToken);
        ResponseEntity<String> userInfoResponse = socialOAuth.requestUserInfo(googleOAuthToken);

        GoogleUser googleUser = socialOAuth.getUserInfo(userInfoResponse);

        Optional<User> optionalUser = userRepository.findByEmail(googleUser.getEmail());
        System.out.println("optionalUser.isEmpty : " + optionalUser.isEmpty());
        if (optionalUser.isEmpty()) {
            User user = new User();
            System.out.println("=====User 객체 생성======");
            user.setEmail(googleUser.getEmail());
            System.out.println("구글 이메일 : " + googleUser.getEmail());
            user.setSocialEmail(googleUser.getEmail());
            user.setSocialProvider("GOOGLE");
            user.setName(googleUser.getName());
            System.out.println("이름 : " + googleUser.getName());
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            user.setRole(userRole);
            userRepository.save(user);
            optionalUser = Optional.of(user);
        }

        User user = optionalUser.get();

        UserDetailsImpl userDetails = UserDetailsImpl.build(user);

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken.getToken());

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .header(HttpHeaders.LOCATION, "https://localhost:3000")
                .build();
    }
}
