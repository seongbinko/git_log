package com.gitlog.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class LoginRequestDto {

    @NotBlank
    private String nickname;

    @NotBlank
    private String password;
}
