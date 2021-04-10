package com.gitlog.controller;

import com.gitlog.dto.AccountRequestDto;
import com.gitlog.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class AccountController {
    private final AccountService accountService;
    private final HttpServletResponse httpServletResponse;

    //회원가입
    @PostMapping("/api/signup")
    public void registerUser(@RequestBody AccountRequestDto accountRequestDto){
        accountService.registerUser(accountRequestDto);
    }

    // 이메일 중복 체크
    @PostMapping("/api/signup/email-check")
    public String email_check(@RequestBody AccountRequestDto accountRequestDto) throws Exception{
        int result = accountService.isDuplicateEmail(accountRequestDto);
        String response;
        if (result == 0){
            return "이메일 사용가능!";
        }else {
            return "이메일 중복! 사용 불가능!";
        }
    }

    // 닉네임 중복 체크
    @PostMapping("/api/signup/nickname-check")
    public String nickname_check(@RequestBody AccountRequestDto accountRequestDto){
        int result = accountService.nickname_check(accountRequestDto);
        if (result == 0){
            return "사용가능한 이름입니다.";
        }else{
            return "해당 이름은 이미 사용중입니다.";
        }
    }
}
