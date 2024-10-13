package com.example.book.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoanVO {
    private int no;
    private String userId;
    private int bookNo;
    private LocalDateTime loanDate;
    private LocalDateTime returnDate;
}
