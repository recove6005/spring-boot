package com.recove6005.room_reservation.domains.vo;

import com.recove6005.room_reservation.enums.RoomState;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class RoomVO {
    private int no;
    private String userId;
    private String title;
    private int price;
    private String description;
    private RoomState state;
}
