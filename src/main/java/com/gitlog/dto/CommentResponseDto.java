package com.gitlog.dto;

import lombok.Data;

@Data
public class CommentResponseDto {

    private Long id;

    private String content;

    private String createdBy;

    public CommentResponseDto(Long id, String content, String createdBy) {
        this.id = id;
        this.content = content;
        this.createdBy = createdBy;
    }
}
