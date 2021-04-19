package com.gitlog.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class AccountRequestDto {

    @NotBlank
    @Length(min = 3, max= 20)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9]{3,20}$")
    private String nickname;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Length(min = 4, max = 50)
    private String password;

    @NotBlank
    @Length(min = 4, max = 50)
    private String passwordConfirm;

    @URL
    @NotBlank
    private String githubUrl;
}
