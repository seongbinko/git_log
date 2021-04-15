package com.gitlog.dto;

import java.time.LocalDateTime;

public class CommentResponseDto {
    private Long id;
    private String content;
    private String createdBy;
    private LocalDateTime createdAt;
    private AccountResponseDto accountResponseDto;

    public CommentResponseDto(Long id, String content, LocalDateTime createdAt, String createdBy, AccountResponseDto accountResponseDto){
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.accountResponseDto = accountResponseDto;
    }
}
