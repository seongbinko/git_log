package com.gitlog.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AccountRequestDto {
    @NotBlank
    @Size(min = 3, max = 20)
    private String nickname;

    @NotBlank
    @Size(min =4, max = 50)
    private String password;

    @NotBlank
    @Size(min =4, max = 50)
    private String passwordConfirm;

    @NotBlank
    @Email
    private String email;

    private MultipartFile imgUrl;

    private String bio;

    @URL
    @NotBlank
    private String githubUrl;
}
