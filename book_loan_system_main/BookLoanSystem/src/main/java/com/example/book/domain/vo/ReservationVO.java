package com.example.book.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationVO {

    private int no;
    private int bookNo;
    private String userId;
    private LocalDateTime date;
}
