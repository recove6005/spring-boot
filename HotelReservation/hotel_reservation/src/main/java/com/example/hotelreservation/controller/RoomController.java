package com.example.hotelreservation.controller;


import com.example.hotelreservation.domains.dto.RoomDTO;
import com.example.hotelreservation.domains.security.SecurityUser;
import com.example.hotelreservation.domains.vo.ReservationVO;
import com.example.hotelreservation.service.ReserveService;
import com.example.hotelreservation.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private ReserveService reserveService;

    @GetMapping
    @ResponseBody
    public List<RoomDTO> get_all_rooms() {
        return roomService.get_all_rooms();
    }

    @GetMapping("/image/{imageName}")
    public ResponseEntity<Resource> get_room_image_file(
            @PathVariable("imageName") String imageName
    ) throws IOException {
        return roomService.get_room_image_file(imageName);
    }


    // 방 세부 내용 페이지
    @GetMapping("/{roomNo}")
    public String get_rooms_main(@PathVariable("roomNo") int roomNo) {
        return "/rooms/main";
    }

    // 방 예약 버튼 눌렀을 시
    @PostMapping("/reservation")
    public String reserve_room(
            @AuthenticationPrincipal SecurityUser securityUser,
            ReservationVO reservationVO
    ) {
        // 로그인 되어있는 유저를 등록
        reservationVO.setUserId(securityUser.getUsername());
        // 해당 유저가 방을 예약
        reserveService.reserve_room(reservationVO);
        return "redirect:/";
    }
}