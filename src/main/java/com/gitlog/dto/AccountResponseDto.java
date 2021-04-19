package com.gitlog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AccountResponseDto {

    private String nickname;

    private String bio;

    private String profileImgUrl;

    private String githubUrl;

    //Todo 기본생성자 없으면, 생성자 변수가 큰걸 자동으로 생성?
    public AccountResponseDto() {}

    public AccountResponseDto(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public AccountResponseDto(String nickname, String bio, String profileImgUrl, String githubUrl) {
        this.nickname = nickname;
        this.bio = bio;
        this.profileImgUrl = profileImgUrl;
        this.githubUrl = githubUrl;
    }
}
