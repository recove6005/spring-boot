package com.example.hotelreservation.domains.vo;

import com.example.hotelreservation.enums.ReserveState;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class ReservationVO {
    private int no;
    private String userId;
    private int roomNo;
    private int reserveDate;
    private int price;
    private ReserveState state;
}
