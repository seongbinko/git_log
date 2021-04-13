package com.gitlog.validator;

import com.gitlog.dto.AccountRequestDto;
import com.gitlog.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class AccountRequestDtoValidator implements Validator {
    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> classes) {
        return classes.isAssignableFrom(AccountRequestDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountRequestDto accountRequestDto = (AccountRequestDto) target;

        if (accountRepository.existsByNickname(accountRequestDto.getNickname())) {
            errors.rejectValue("nickname", "invalid.nickname", new Object[]{accountRequestDto.getNickname()}, "이미 사용중인 닉네임 입니다.");
        }
        if (accountRepository.existsByEmail(accountRequestDto.getEmail())) {
            errors.rejectValue("email", "invalid.email", new Object[]{accountRequestDto.getEmail()}, "이미 사용중인 이메일 입니다.");
        }
        if (!accountRequestDto.getPassword().equals(accountRequestDto.getPassword_check())) {
            errors.rejectValue("password", "wrong.value", "입력한 패스워드가 서로 일치하지 않습니다.");
        }
    }
}
