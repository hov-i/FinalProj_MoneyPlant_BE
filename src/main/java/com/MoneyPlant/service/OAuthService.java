package com.MoneyPlant.service;

import com.MoneyPlant.config.GoogleOAuth;
import com.MoneyPlant.dto.GetGoogleOAuthRes;
import com.MoneyPlant.dto.GoogleOAuthToken;
import com.MoneyPlant.dto.GoogleUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class OAuthService {

    // 다른 OAuth 인증이 필요하면 타입 걸러주는 함수 필요합니다
    private final GoogleOAuth socialOAuth;

    public String request() throws IOException {
        String redirectURL;
        redirectURL=socialOAuth.getOAuthRedirectURL();
        return redirectURL;
    }

    public GetGoogleOAuthRes OAuthLogin(String code) throws JsonProcessingException {
        ResponseEntity<String> accessToken =socialOAuth.requestAccessToken(code);

        GoogleOAuthToken googleOAuthToken =socialOAuth.getAccessToken(accessToken);

        ResponseEntity<String> userInfoResponse=socialOAuth.requestUserInfo(googleOAuthToken);
        System.out.println("requestUserInfo 완료");

        GoogleUser googleUser =socialOAuth.getUserInfo(userInfoResponse);
        System.out.println("getUserInfo 완료");

        String user_id = googleUser.getEmail();

        return new GetGoogleOAuthRes("1234",1,"asdf", googleOAuthToken.getToken_type());
    }
}