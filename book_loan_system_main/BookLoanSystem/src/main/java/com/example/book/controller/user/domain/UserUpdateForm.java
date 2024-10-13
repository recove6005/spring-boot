package com.example.book.controller.user.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserUpdateForm {

    @NotBlank(message = "필수 입력 정보입니다.")
    private String pw;
    @NotBlank(message = "필수 입력 정보입니다.")
    private String name;
    @NotBlank(message = "필수 입력 정보입니다.")
    private String tel;
    @NotBlank(message = "필수 입력 정보입니다.")
    private String addr;
}
