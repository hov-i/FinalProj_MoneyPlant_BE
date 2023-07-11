package com.MoneyPlant.controller;
import com.MoneyPlant.dto.SignupRequest;
import com.MoneyPlant.service.MailService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@CrossOrigin(origins = "https://localhost:3000", allowedHeaders = "*")
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;


    @ApiOperation(value = "이메일 인증코드 전송", notes = "전송한 인증코드를 반환한다.", response = Map.class)
    @PostMapping("/sendmail")
    public ResponseEntity<Map<String, Object>> sendMailAndUpdatePwd(@RequestBody Map<String, String> map) {
        String type = map.get("type");
        String email = map.get("email");

        boolean isMailAndPwdUpdated = mailService.sendMailAndUpdatePwd(type, email);
        Map<String, Object> resultMap = new HashMap<>();
        if (isMailAndPwdUpdated) {
            resultMap.put("message", "SUCCESS");
            HttpStatus status = HttpStatus.OK;
            return new ResponseEntity<>(resultMap, status);
        } else {
            resultMap.put("message", "메일 보내는 데 실패했습니다.");
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(resultMap, status);
        }
    }
}




//    @ApiOperation(value = "이메일 인증코드 전송", notes = "전송한 인증코드를 반환한다.", response = Map.class)
//    @PostMapping("/sendmail")
//    public ResponseEntity<Map<String, Object>> sendMail(@RequestBody Map<String, String> map) {
//        Map<String, Object> resultMap = new HashMap<>();
//        HttpStatus status = null;
//
//        String code = mailService.sendMail(map.get("type"), map.get("email"));
//        if (code.equals("error")) {
//            resultMap.put("message", FAIL);
//            status = HttpStatus.ACCEPTED;
//        } else {
//            resultMap.put("message", SUCCESS);
//            resultMap.put("code", code);
//            status = HttpStatus.ACCEPTED;
//        }
//
//        return new ResponseEntity<>(resultMap, status);
//    }
//
//    // 이메일이랑 생성 코드를 일치시킨 후 PW를 주입
//    @PostMapping("/updatePassword")
//    public ResponseEntity<String> updatePwd(@RequestParam String email, @RequestParam String code) {
//        boolean isPasswordUpdated = mailService.updatePwd(email, code);
//
//        if (isPasswordUpdated) {
//            return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
//        } else {
//            return ResponseEntity.badRequest().body("비밀번호 변경에 실패했습니다.");
//        }
//    }
//}