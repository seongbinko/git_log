package com.gitlog.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileRequestDto {

    private String githubUrl;
    private String password;
    private String passwordConfirm;
    private String bio;
    private MultipartFile profileImg;
}
