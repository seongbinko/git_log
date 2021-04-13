package com.gitlog.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class EmailRequestDto {
    @Email
    private String email;
}
