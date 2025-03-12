package com.recove6005.room_reservation.domains.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class RatingVO {
    private String id;
    private String roomNo;
    private int score;
}
