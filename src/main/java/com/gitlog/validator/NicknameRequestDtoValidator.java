package com.gitlog.validator;

import com.gitlog.dto.NicknameRequestDto;
import com.gitlog.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class NicknameRequestDtoValidator implements Validator {
    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(NicknameRequestDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NicknameRequestDto nicknameRequestDto = (NicknameRequestDto) target;

        if (accountRepository.existsByNickname(nicknameRequestDto.getNickname())) {
            errors.rejectValue("nickname", "invalid.nickname", new Object[]{nicknameRequestDto.getNickname()}, "이미 사용중인 닉네임입니다.");
        }
    }
}
