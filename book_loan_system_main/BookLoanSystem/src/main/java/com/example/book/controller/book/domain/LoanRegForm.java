package com.example.book.controller.book.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoanRegForm {
    private String userId;
    private int bookNo;
    private LocalDateTime loanDate;
    private LocalDateTime returnDate;
}
