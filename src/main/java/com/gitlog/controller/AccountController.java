package com.gitlog.controller;

import com.gitlog.config.JwtTokenProvider;
import com.gitlog.dto.AccountRequestDto;
import com.gitlog.dto.EmailRequestDto;
import com.gitlog.dto.LoginRequestDto;
import com.gitlog.dto.NicknameRequestDto;
import com.gitlog.model.Account;
import com.gitlog.repository.AccountRepository;
import com.gitlog.service.AccountService;
import com.gitlog.validator.AccountRequestDtoValidator;
import com.gitlog.validator.EmailRequestDtoValidator;
import com.gitlog.validator.LoginRequestDtoValidator;
import com.gitlog.validator.NicknameRequestDtoValidator;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountService accountService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AccountRepository accountRepository;

    private final AccountRequestDtoValidator accountRequestDtoValidator;
    private final LoginRequestDtoValidator loginRequestDtoValidator;
    private final EmailRequestDtoValidator emailRequestDtoValidator;
    private final NicknameRequestDtoValidator nicknameRequestDtoValidator;

    @InitBinder("accountRequestDto")
    public void signUpBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(accountRequestDtoValidator);
    }

    @InitBinder("emailRequestDto")
    public void emailBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(emailRequestDtoValidator);
    }

    @InitBinder("nicknameRequestDto")
    public void nicknameBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(nicknameRequestDtoValidator);
    }

    @InitBinder("loginRequestDto")
    public void loginBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(loginRequestDtoValidator);
    }

    @PostMapping("/api/signup")
    public ResponseEntity registerAccount(@Valid @RequestBody AccountRequestDto accountRequestDto, Errors errors){
        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        accountService.registerAccount(accountRequestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/signup/email-check")
    public ResponseEntity emailCheck(@Valid @RequestBody EmailRequestDto emailRequestDto, Errors errors) {
        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/signup/nickname-check")
    public ResponseEntity emailCheck(@Valid @RequestBody NicknameRequestDto nicknameRequestDto, Errors errors) {
        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        return ResponseEntity.ok().build();
    }

    // 로그인
    @PostMapping("/api/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequestDto loginRequestDto, Errors errors) {
        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        Account account = accountRepository.findByNickname(loginRequestDto.getNickname()).orElse(null);
        JsonObject obj = new JsonObject();
        obj.addProperty("token", jwtTokenProvider.createToken(account.getNickname(), account.getRoles()));
        return ResponseEntity.ok().body(obj.toString());
    }
}
