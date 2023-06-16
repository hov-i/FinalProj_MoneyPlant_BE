package com.MoneyPlant.config;

import com.MoneyPlant.dto.GoogleOAuthToken;
import com.MoneyPlant.dto.GoogleUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoogleOAuth implements SocialOAuth{

    @Value("${app.googleUrl}")
    private String GOOGLE_SNS_LOGIN_URL;

    @Value("${app.googleClientId}")
    private String GOOGLE_SNS_CLIENT_ID;

    @Value("${app.googleRedirectUrl}")
    private String GOOGLE_SNS_CALLBACK_URL;

    @Value("${app.googleClientSecret}")
    private String GOOGLE_SNS_CLIENT_SECRET;

    @Value("${app.googleScope}")
    private String GOOGLE_DATA_ACCESS_SCOPE;

    // String 값을 객체로 바꾸는 Mapper
    private final ObjectMapper objectMapper;


    // Google API로 요청을 보내고 받을 객체입니다.
    private final RestTemplate restTemplate;

    @Override
    public String getOAuthRedirectURL() {
        Map<String,Object> params = new HashMap<>();

        params.put("scope",GOOGLE_DATA_ACCESS_SCOPE);
        params.put("response_type","code");
        params.put("access_type","offline");
        params.put("client_id",GOOGLE_SNS_CLIENT_ID);
        params.put("redirect_uri",GOOGLE_SNS_CALLBACK_URL);

        String parameterString=params.entrySet().stream()
                .map(x->x.getKey()+"="+x.getValue())
                .collect(Collectors.joining("&"));
        String redirectURL=GOOGLE_SNS_LOGIN_URL+"?"+parameterString;
        log.info("redirect-URL={}", redirectURL);
        return redirectURL;
    }

    @Override
    public ResponseEntity<String> requestAccessToken(String code) {
        String GOOGLE_TOKEN_REQUEST_URL = "https://OAuth2.googleapis.com/token";
        RestTemplate restTemplate=new RestTemplate();
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("client_secret", GOOGLE_SNS_CLIENT_SECRET);
        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);
        params.put("grant_type", "authorization_code");
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(GOOGLE_TOKEN_REQUEST_URL, params, String.class);
        return stringResponseEntity;
    }

    @Override
    public GoogleOAuthToken getAccessToken(ResponseEntity<String> accessToken) throws JsonProcessingException {
        log.info("accessTokenBody: {}",accessToken.getBody());
        return objectMapper.readValue(accessToken.getBody(),GoogleOAuthToken.class);
    }

    @Override
    public ResponseEntity<String> requestUserInfo(GoogleOAuthToken googleOAuthToken) {
        String GOOGLE_USERINFO_REQUEST_URL= "https://www.googleapis.com/userinfo/v2/me";
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(headers);
        headers.add("access_token", googleOAuthToken.getAccess_token());
        System.out.println("restTemplate : " + restTemplate.toString());
        // 여기가 안됨
        ResponseEntity<String> exchange = restTemplate.exchange(GOOGLE_USERINFO_REQUEST_URL, HttpMethod.GET, request, String.class);
        System.out.println("exchange : " + exchange);
        System.out.println(exchange.getBody());
        return exchange;
    }

    @Override
    public GoogleUser getUserInfo(ResponseEntity<String> userInfoResponse) throws JsonProcessingException {
        GoogleUser googleUser = objectMapper.readValue(userInfoResponse.getBody(), GoogleUser.class);
        return googleUser;
    }
}