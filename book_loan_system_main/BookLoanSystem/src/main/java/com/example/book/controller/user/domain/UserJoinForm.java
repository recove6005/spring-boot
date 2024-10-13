package com.example.book.controller.user.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserJoinForm {

    @NotBlank(message = "필수 입력 정보입니다.")
    private String id;
    @NotBlank(message = "필수 입력 정보입니다.")
    private String pw1;
    @NotBlank(message = "필수 입력 정보입니다.")
    private String pw2;
    @NotBlank(message = "필수 입력 정보입니다.")
    private String name;
    @NotBlank(message = "필수 입력 정보입니다.")
    private String tel;
    @NotBlank(message = "필수 입력 정보입니다.")
    private String addr;
}