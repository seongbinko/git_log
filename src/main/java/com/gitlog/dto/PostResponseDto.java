package com.gitlog.dto;

import lombok.Data;

@Data
public class PostResponseDto {
    private Long id;
    private String content;
    private String imgUrl;
    private String createdBy;
    private String profile_imgUrl;
    public PostResponseDto(Long id, String content, String imgUrl, String createdBy, String profile_imgUrl){
        this.id = id;
        this.content = content;
        this.imgUrl = imgUrl;
        this.createdBy = createdBy;
        this.profile_imgUrl = profile_imgUrl;
    }
}
