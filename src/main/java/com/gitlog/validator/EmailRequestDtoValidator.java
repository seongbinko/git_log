package com.gitlog.validator;

import com.gitlog.dto.EmailRequestDto;
import com.gitlog.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class EmailRequestDtoValidator implements Validator {
    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(EmailRequestDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EmailRequestDto emailRequestDto = (EmailRequestDto) target;

        if (accountRepository.existsByEmail(emailRequestDto.getEmail())) {
            errors.rejectValue("email", "invalid.email", new Object[]{emailRequestDto.getEmail()}, "이미 사용중인 이메일 입니다.");
        }
    }
}
