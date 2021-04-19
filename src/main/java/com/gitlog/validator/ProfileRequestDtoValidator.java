package com.gitlog.validator;

import com.gitlog.dto.ProfileRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ProfileRequestDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(ProfileRequestDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProfileRequestDto profileRequestDto = (ProfileRequestDto) target;
        if(StringUtils.hasText(profileRequestDto.getPassword()) && StringUtils.hasText(profileRequestDto.getPasswordConfirm())) {
            if(!profileRequestDto.getPassword().equals(profileRequestDto.getPasswordConfirm())) {
                errors.rejectValue("password", "wrong.value", "입력한 패스워드가 서로 일치하지 않습니다.");
            }
        }
    }
}
