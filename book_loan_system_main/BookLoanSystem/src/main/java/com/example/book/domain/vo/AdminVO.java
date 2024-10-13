package com.example.book.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class AdminVO {
    private String id;
    private String pw;
    private String tel;
}