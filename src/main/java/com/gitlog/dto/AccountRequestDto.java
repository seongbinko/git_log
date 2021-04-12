package com.gitlog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class AccountRequestDto {

    private String nickname;

    private String email;

    private String password;

    private String passwordConfirm;

    private String githubUrl;
}
