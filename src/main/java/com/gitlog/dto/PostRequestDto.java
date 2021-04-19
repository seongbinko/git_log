package com.gitlog.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PostRequestDto {

    @NotBlank
    private String content;

    @NotNull
    private MultipartFile img;
}
