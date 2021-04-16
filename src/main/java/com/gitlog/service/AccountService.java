package com.gitlog.service;

import com.gitlog.dto.AccountRequestDto;
import com.gitlog.dto.ProfileRequestDto;
import com.gitlog.model.Account;
import com.gitlog.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@RequiredArgsConstructor
@Service
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    public Account registerAccount(AccountRequestDto requestDto){
        return accountRepository.save(
                Account.builder()
                        .nickname(requestDto.getNickname())
                        .email(requestDto.getEmail())
                        .password(passwordEncoder.encode(requestDto.getPassword()))
                        .githubUrl(requestDto.getGithubUrl())
                        .roles(Collections.singletonList("ROLE_USER"))
                        .build()
        );
    }
    public void uploadProfile(ProfileRequestDto profileRequestDto, String profileImgUrl, Account account) {
        profileRequestDto.setPassword(passwordEncoder.encode(profileRequestDto.getPassword()));
        account.updateProfile(profileRequestDto, profileImgUrl);
    }
}
