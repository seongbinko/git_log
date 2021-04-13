package com.gitlog.service;

import com.gitlog.dto.LoginRequestDto;
import com.gitlog.dto.AccountRequestDto;
import com.gitlog.model.Account;
import com.gitlog.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    //이메일 중복크 체크
    public ResponseEntity<String> isDuplicateEmail(AccountRequestDto accountRequestDto){
        String email = accountRequestDto.getEmail();
        Optional<Account> found = accountRepository.findByEmail(email);
        if (found.isPresent()){
            return new ResponseEntity<>("사용 가능한 이메일 주소입니다.", HttpStatus.OK);
        }
        return new ResponseEntity<>("이미 사용중인 이메일 주소입니다",HttpStatus.OK);
    }
    //닉네임 중복 체크
    public ResponseEntity<String> nickname_check(AccountRequestDto accountRequestDto){
        String nickname = accountRequestDto.getNickname();
        Optional<Account> found = accountRepository.findByNickname(nickname);
        if (found.isPresent()){
            return new ResponseEntity<>("사용 가능한 이름입니다.", HttpStatus.OK);
        }
        return new ResponseEntity<>("이미 사용중인 이름입니다",HttpStatus.OK);
    }


    //사용자 추가
    public Account registerAccount(@Valid AccountRequestDto accountRequestDto){

        return accountRepository.save(
                Account.builder()
                .nickname(accountRequestDto.getNickname())
                .email(accountRequestDto.getEmail())
                .password(passwordEncoder.encode(accountRequestDto.getPassword()))
                .githubUrl(accountRequestDto.getGithubUrl())
                .bio(accountRequestDto.getBio())
                .roles(Collections.singletonList("ROLE_USER"))
                .build()
        );
    }
    //사용자 수정
    public ResponseEntity<String> modifyAccount(AccountRequestDto accountRequestDto){
        Account.builder()
                .password(passwordEncoder.encode(accountRequestDto.getPassword()))
                .githubUrl(accountRequestDto.getGithubUrl())
                .bio(accountRequestDto.getBio())
                .build();
        return new ResponseEntity<>("성공적으로 수정하였습니다", HttpStatus.OK);
    }

    public Account login(LoginRequestDto loginRequestDto){
        return accountRepository.findByNickname(loginRequestDto.getNickname()).orElse(null);
    }
}
