package com.gitlog.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AccountRequestDto implements Serializable {
    private String nickname;
    private String password;
    private String password_check;
    private String email;
    private String bio;
    private String imgUrl;
    private String githubUrl;
}
