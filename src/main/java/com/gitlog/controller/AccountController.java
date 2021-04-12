package com.gitlog.controller;

import com.gitlog.config.JwtTokenProvider;
import com.gitlog.dto.AccountRequestDto;
import com.gitlog.model.Account;
import com.gitlog.repository.AccountRepository;
import com.gitlog.service.AccountService;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AccountRepository accountRepository;

    @PostMapping("/api/signup")
    public ResponseEntity registerAccount(@RequestBody AccountRequestDto requestDto){
        Account account = accountRepository.findByNickname(requestDto.getNickname()).orElse(null);
        if(account != null) {

            if (account.getNickname().equals(requestDto)) {
                return ResponseEntity.badRequest().build();
                //throw new IllegalArgumentException("중복된 사용자 ID가 존재합니다.");
            }
            if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
                return ResponseEntity.badRequest().build();
                //throw new IllegalArgumentException("pw가 일치하지 않습니다.");
            }
        }
        accountService.registerAccount(requestDto);
        return ResponseEntity.ok().build();
    }

    // 로그인
    @PostMapping("/api/login")
    public String login(@RequestBody Map<String, String> loginInfo) {

        Account account = accountRepository.findByNickname(loginInfo.get("nickname")).orElseThrow(() -> new IllegalArgumentException("가입되지 않은 nickname 입니다."));

        if (!passwordEncoder.matches(loginInfo.get("password"), account.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        JsonObject obj = new JsonObject();
        obj.addProperty("token", jwtTokenProvider.createToken(account.getNickname(), account.getRoles()));
        return obj.toString();
    }
}
