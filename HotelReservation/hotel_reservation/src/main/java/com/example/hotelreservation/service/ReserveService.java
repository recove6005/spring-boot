package com.example.hotelreservation.service;

import com.example.hotelreservation.domains.dto.ReservationDTO;
import com.example.hotelreservation.domains.vo.ReservationVO;
import com.example.hotelreservation.domains.vo.UserVO;
import com.example.hotelreservation.mapper.ReserveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReserveService {
    @Autowired
    private ReserveMapper reserveMapper;

    // 사용자 방 예약 서비스
    public void reserve_room(ReservationVO reservationVO) {
        reserveMapper.reserve_room(reservationVO);
    }

    // 해당 사옹자의 예약 정보 조회
    public List<ReservationDTO> get_reserve_info_by_user(UserVO userVO){
        return reserveMapper.get_reserve_info_by_user(userVO);
    }
}