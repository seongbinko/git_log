package com.gitlog.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
public class PostUpdateRequestDto {

    private String content;

    private MultipartFile img;
}
