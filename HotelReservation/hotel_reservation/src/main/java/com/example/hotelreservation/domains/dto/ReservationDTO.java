package com.example.hotelreservation.domains.dto;

import com.example.hotelreservation.domains.vo.ReservationVO;
import com.example.hotelreservation.domains.vo.RoomImagesVO;
import com.example.hotelreservation.domains.vo.RoomVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
public class ReservationDTO {
    private int no;
    private ReservationVO reservationVO;
    private RoomVO roomVo;
    private List<RoomImagesVO> roomImagesVOS;

}