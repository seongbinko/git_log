package com.gitlog.controller;

import com.gitlog.config.JwtTokenProvider;
import com.gitlog.dto.AccountRequestDto;
import com.gitlog.model.Account;
import com.gitlog.repository.AccountRepository;
import com.gitlog.service.AccountService;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class AccountController {
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

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
    //Jwttoken 발급 로그인
    @PostMapping("/api/login")
    public String login(@RequestBody AccountRequestDto accountRequestDto){
        Account account = accountRepository.findByNickname(accountRequestDto.getNickname()).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        if (!passwordEncoder.matches(accountRequestDto.getPassword(), account.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(account.getNickname(), account.getRoles());
    }
}
