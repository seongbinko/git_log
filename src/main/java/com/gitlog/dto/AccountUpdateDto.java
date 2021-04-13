package com.gitlog.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AccountUpdateDto {

    @NotBlank
    @Size(min =4, max = 50)
    private String password;

    private String imgUrl;

    private String bio;

    @URL
    @NotBlank
    private String githubUrl;
}
