package com.gitlog.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class EmailRequestDto {

    @Email
    @NotBlank
    private String email;
}
