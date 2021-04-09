package com.gitlog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRequestDto {
    private String nickname;
    private String password;
    private String password_check;
    private String email;
    private String bio;
    private String imgUrl;
    private String githubUrl;
}
