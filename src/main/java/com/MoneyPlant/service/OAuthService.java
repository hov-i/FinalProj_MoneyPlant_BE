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
    // SocialOAuth 인터페이스를 상속받아 다른 소셜 OAuth 만들고 해당하는 로직 구현
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

        // 여기까지 하면 토큰으로 유저정보 가져오기 까지 한 것
        // 왜 유저정보를 가져왔나?

        String user_id = googleUser.getEmail();
        System.out.println(user_id);

        // 이 이메일 값으로 DB에 있는 값과 비교
        // 없으면 신규 회원으로 가입시키고
        // 있으면 로그인 인증이 된 것이죠

        // 로그아웃하면 access token이랑 refresh token 없애버려야함
        // 서버 db에는 access token, refresh token email 정보만

        // 일반 로그인도 구현하면
        // jwt 구현해야할텐데

        return new GetGoogleOAuthRes("1234",1,"asdf", googleOAuthToken.getToken_type());
    }
}