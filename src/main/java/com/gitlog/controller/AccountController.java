package com.gitlog.controller;

import com.gitlog.dto.AccountRequestDto;
import com.gitlog.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.aspectj.bridge.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class AccountController {
    private final AccountService accountService;
    private final HttpServletResponse httpServletResponse;

    @PostMapping("/api/signup")
    public void registerUser(@RequestBody AccountRequestDto accountRequestDto){
        accountService.registerUser(accountRequestDto);
    }

    @PostMapping("/api/signup/email-check")
    public String email_check(@RequestBody AccountRequestDto accountRequestDto){
        accountService.email_check(accountRequestDto);
        return "이미 사용중인 이메일 주소입니다.";
    }

}
