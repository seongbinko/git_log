package com.gitlog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponseDto {
    private String nickname;
    private String bio;
    private String createdBy;
    private String profileImgUrl;
    private String githubUrl;

    public AccountResponseDto(){}

    public AccountResponseDto(String profileImgUrl){
        this.profileImgUrl = profileImgUrl;
    }

    public AccountResponseDto(String nickname, String bio, String createdBy, String profileImgUrl, String githubUrl){
        this.nickname = nickname;
        this.bio = bio;
        this.createdBy = createdBy;
        this.profileImgUrl = profileImgUrl;
        this.githubUrl = githubUrl;
    }
}
