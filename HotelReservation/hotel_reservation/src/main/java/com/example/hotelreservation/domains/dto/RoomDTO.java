package com.example.hotelreservation.domains.dto;

import com.example.hotelreservation.domains.vo.RatingVO;
import com.example.hotelreservation.domains.vo.RoomVO;
import com.example.hotelreservation.domains.vo.RoomImagesVO;
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
    private List<RatingVO> ratingVO;
    private List<RoomImagesVO> roomImagsVOS;
}