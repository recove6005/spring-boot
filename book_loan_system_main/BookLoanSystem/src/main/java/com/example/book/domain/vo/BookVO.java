package com.example.book.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString
@NoArgsConstructor
public class BookVO {
    // 책 번호(no)는 시퀀스, 상태(state)는 대출가능으로 자동저장
    private int no;
    private String title;
    private String author;
    private String publisher;
    private String lib;
    private int state;
    private String target;
    private String type;
    private String imagePath;

}
