package com.gitlog.controller;

import com.gitlog.config.JwtTokenProvider;
import com.gitlog.config.UserDetailsImpl;
import com.gitlog.config.uploader.Uploader;
import com.gitlog.dto.*;
import com.gitlog.model.Account;
import com.gitlog.service.AccountService;
import com.gitlog.validator.EmailRequestDtoValidator;
import com.gitlog.validator.LoginRequestDtoValidator;
import com.gitlog.validator.AccountRequestDtoValidator;
import com.gitlog.validator.NicknameRequestDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import javax.validation.Valid;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class AccountController {
    private final AccountService accountService;
    private final JwtTokenProvider jwtTokenProvider;
    private final Uploader uploader;

    private final AccountRequestDtoValidator accountRequestDtoValidator;
    private final LoginRequestDtoValidator loginRequestDtoValidator;
    private final NicknameRequestDtoValidator nicknameRequestDtoValidator;
    private final EmailRequestDtoValidator emailRequestDtoValidator;

    //회원가입에 필요한 부분 체크해주는 부분.
    @InitBinder("accountRequestDto")
    public void signUpBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(accountRequestDtoValidator);
    }

    //로그인 닉네임 + 비밀번호 검증 해주는 Validator 부분.
    @InitBinder("loginRequestDto")
    public void loginBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(loginRequestDtoValidator);
    }

    //닉네임 중복체크 해주는 Validator 부분.
    @InitBinder("nicknameRequestDto")
    public void nicknameBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(nicknameRequestDtoValidator);
    }

    //이메일 중복체크 해주는 Validator 부분.
    @InitBinder("emailRequestDto")
    public void emailBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(emailRequestDtoValidator);
    }


    @PutMapping("/api/profile")
    public ResponseEntity upload(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam("data") MultipartFile file, @RequestParam(value = "bio", required = false) String bio, @RequestParam(value = "githubUrl", required = false) String githubUrl, @RequestParam(value = "password", required = false) String password)throws IOException{
        return accountService.modifyAccount(file, githubUrl,bio, password, userDetails);

    }

    //회원가입
    @PostMapping("/api/signup")
    public ResponseEntity<String> registerAccount(@Valid @RequestBody AccountRequestDto accountRequestDto, Errors errors, @RequestParam("data") MultipartFile file) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        } else {
            try {
                uploader.upload(file, "static");
            } catch (IOException e) {
                e.getSuppressed();
            }

            accountService.registerAccount(accountRequestDto);
            return new ResponseEntity<>("성공적으로 등록 하였습니다", HttpStatus.OK);
        }
    }

    //Jwttoken 발급 로그인
    @PostMapping("/api/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequestDto loginRequestDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }
        Account account = accountService.login(loginRequestDto);
        return ResponseEntity.ok().body(jwtTokenProvider.createToken(account.getNickname(), account.getRoles()));
    }

    //닉네임 중복확인
    @PostMapping("/api/signup/nickname-check")
    public ResponseEntity checkNickname(@Valid @RequestBody NicknameRequestDto nicknameRequestDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }
        return new ResponseEntity<>("사용 가능한 닉네임 입니다", HttpStatus.OK);
    }

    //이메일 중복확인
    @PostMapping("/api/signup/email-check")
    public ResponseEntity checkEmail(@Valid @RequestBody EmailRequestDto emailRequestDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }
        return new ResponseEntity<>("사용 가능한 이메일 입니다", HttpStatus.OK);
    }

    //사용자 수정
//    @PutMapping("/api/profile")
//    public ResponseEntity<String> modifyAccount(@Valid @RequestBody AccountUpdateDto accountUpdateDto, Errors errors) {
//        if (errors.hasErrors()){
//            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
//        }
//        return accountService.modifyAccount(accountUpdateDto);
//    }
}
