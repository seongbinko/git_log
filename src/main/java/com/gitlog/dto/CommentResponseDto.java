package com.gitlog.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponseDto {

    private Long id;

    private String content;

    private LocalDateTime createdAt;

    private String createdBy;

    private AccountResponseDto accountResponseDto;

    public CommentResponseDto(Long id, String content, LocalDateTime createdAt, String createdBy) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public CommentResponseDto(Long id, String content, LocalDateTime createdAt, String createdBy, AccountResponseDto accountResponseDto) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.accountResponseDto = accountResponseDto;
    }
}
