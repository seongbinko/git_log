package com.gitlog.service;

import com.gitlog.config.JwtTokenProvider;
import com.gitlog.dto.AccountRequestDto;
import com.gitlog.model.Account;
import com.gitlog.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    //이메일 중복크 체크
    public int isDuplicateEmail(AccountRequestDto accountRequestDto){
        String email = accountRequestDto.getEmail();
        Optional<Account> found = accountRepository.findByEmail(email);
        if (found.isPresent()){
            return 1;
        }
        else {
            return 0;
        }
    }
    //닉네임 중복 체크
    public int nickname_check(AccountRequestDto accountRequestDto){
        String nickname = accountRequestDto.getNickname();
        Optional<Account> found = accountRepository.findByNickname(nickname);
        if (found.isPresent()){
            return 1;
        }
        return 0;
    }

    //사용자 추가
    public Account registerUser(AccountRequestDto accountRequestDto){
        String nickname = accountRequestDto.getNickname();
        Optional<Account> account_found = accountRepository.findByNickname(nickname);
        if (account_found.isPresent()){
            throw new IllegalArgumentException("이미 사용중인 사용자 이름입니다.");
        }
        String email = accountRequestDto.getEmail();
        Optional<Account> email_found = accountRepository.findByEmail(email);
        if (email_found.isPresent()){
            throw new IllegalArgumentException("이미 사용중인 이메일 입니다.");
        }
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
}
