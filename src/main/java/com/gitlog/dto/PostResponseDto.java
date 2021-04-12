package com.gitlog.dto;

import lombok.Data;

@Data
public class PostResponseDto {
    private Long id;
    private String content;
    private String imgUrl;
    private String createdBy;
    public PostResponseDto(Long id, String content, String imgUrl, String createdBy){
        this.id = id;
        this.content = content;
        this.imgUrl = imgUrl;
        this.createdBy = createdBy;
    }
}
