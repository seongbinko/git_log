package com.gitlog.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostRequestDto {

    @NotBlank
    private String content;

    @NotBlank
    private String imgUrl;
}
