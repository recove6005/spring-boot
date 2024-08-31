package com.example.hotelreservation.mapper;

import com.example.hotelreservation.domains.dto.ReservationDTO;
import com.example.hotelreservation.domains.vo.ReservationVO;
import com.example.hotelreservation.domains.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReserveMapper {
    void reserve_room(ReservationVO reservationVO);
    List<ReservationDTO> get_reserve_info_by_user(UserVO userVO);
}