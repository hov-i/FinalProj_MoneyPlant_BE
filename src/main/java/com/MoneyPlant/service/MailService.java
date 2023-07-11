package com.MoneyPlant.service;

import com.MoneyPlant.dto.SignupRequest;

public interface MailService {
    String makeCode(int size);
//    String makeHtml(String type, String code);
    boolean sendMailAndUpdatePwd(String type, String email);
//    String sendMail(String type, String email);
//    boolean updatePwd(String email, String code);
}
