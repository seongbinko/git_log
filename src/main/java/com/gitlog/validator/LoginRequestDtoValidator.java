package com.gitlog.validator;

import com.gitlog.dto.AccountRequestDto;
import com.gitlog.dto.LoginRequestDto;
import com.gitlog.model.Account;
import com.gitlog.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class LoginRequestDtoValidator implements Validator {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(LoginRequestDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoginRequestDto loginRequestDto = (LoginRequestDto) target;

        if(!accountRepository.existsByNickname(loginRequestDto.getNickname())){
            errors.rejectValue("nickname", "invalid.nickname", new Object[]{loginRequestDto.getNickname()},"존재하지 않는 닉네임입니다.");
        }

        Account account = accountRepository.findByNickname(loginRequestDto.getNickname()).orElse(null);
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), account.getPassword())) {
            errors.rejectValue("password", "invalid.password", new Object[]{loginRequestDto.getPassword()},"패스워드가 일치하지 않습니다.");
        }
    }
}
