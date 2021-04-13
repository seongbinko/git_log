package com.gitlog.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class EmailRequestDto {

    @NotBlank
    @Email
    private String email;
}
