package com.gitlog.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentRequestDto {

    @NotBlank
    private String content;
}
