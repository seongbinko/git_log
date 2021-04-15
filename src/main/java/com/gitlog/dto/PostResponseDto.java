package com.gitlog.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostResponseDto {

    private Long id;

    private String content;

    private String imgUrl;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime modifiedAt;

    private List<CommentResponseDto> comments = new ArrayList<>();

    private AccountResponseDto accountResponseDto;

    private int commentCnt;

    private int heartCnt;

    public PostResponseDto() {}

    public PostResponseDto(Long id, String content, String imgUrl, String createdBy) {
        this.id = id;
        this.content = content;
        this.imgUrl = imgUrl;
        this.createdBy = createdBy;
    }

    public PostResponseDto(Long id, String content, String imgUrl, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, List<CommentResponseDto> comments, AccountResponseDto accountResponseDto) {
        this.id = id;
        this.content = content;
        this.imgUrl = imgUrl;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.comments = comments;
        this.accountResponseDto = accountResponseDto;
    }

    public PostResponseDto(Long id, String content, String imgUrl, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, List<CommentResponseDto> comments, AccountResponseDto accountResponseDto, int commentCnt, int heartCnt) {
        this.id = id;
        this.content = content;
        this.imgUrl = imgUrl;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.comments = comments;
        this.accountResponseDto = accountResponseDto;
        this.commentCnt = commentCnt;
        this.heartCnt = heartCnt;
    }
}
