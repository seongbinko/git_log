package com.gitlog.service;

import com.gitlog.dto.AccountRequestDto;
import com.gitlog.model.Account;
import com.gitlog.model.Message;
import com.gitlog.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public String email_check(AccountRequestDto accountRequestDto){
        String email = accountRequestDto.getEmail();
        Optional<Account> found = accountRepository.findByEmail(email);
        if (found.isPresent()){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        return null;
    }

    //사용자 추
    public void registerUser(AccountRequestDto accountRequestDto){
        String nickname = accountRequestDto.getNickname();
        Optional<Account> found = accountRepository.findByNickname(nickname);
        if (found.isPresent()){
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
        String password = accountRequestDto.getPassword();
        String password_check = accountRequestDto.getPassword_check();
        if (!password.equals(password_check)){
            throw new IllegalArgumentException("비밀번호가 일치 하지 않습니다.");
        }
        password = passwordEncoder.encode(accountRequestDto.getPassword());

        String email = accountRequestDto.getEmail();
        String bio = accountRequestDto.getBio();
        String github_url = accountRequestDto.getGithubUrl();
        String img_url = accountRequestDto.getImgUrl();

        Account account = new Account(nickname, password, email, bio, github_url, img_url);
        accountRepository.save(account);
    }
}
