package com.recove6005.room_reservation.domains.dto;

import com.recove6005.room_reservation.domains.vo.RatingVO;
import com.recove6005.room_reservation.domains.vo.RoomVO;
import com.recove6005.room_reservation.domains.vo.RoomImageVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
public class RoomDTO {
    private int roomNo;
    private RoomVO roomVO;
    private List<RatingVO> ratingVOList;
    private List<RoomImageVO> roomImagesVOList;
}
