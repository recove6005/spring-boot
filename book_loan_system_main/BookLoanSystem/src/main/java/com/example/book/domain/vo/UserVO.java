package com.example.book.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class UserVO {
    private String id;
    private String pw;
    private String name;
    private String tel;
    private String addr;
}