package com.recove6005.room_reservation.controller;

import com.recove6005.room_reservation.domains.dto.RoomDTO;
import com.recove6005.room_reservation.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    // 모든 room 데이터 가져오기
    @GetMapping
    @ResponseBody
    public List<RoomDTO> getAllRooms() {
        return roomService.getAllRooms();
    }

    // room 사진 가져오기
    @GetMapping("/images/{imageName}")
    public ResponseEntity<Resource> getRoomImageFile(
            @PathVariable("imageName") String imageName
    ) throws IOException {
        return roomService.getRoomImageFile(imageName);
    }

    // room 세부 내용



    @GetMapping("/reservation")
    public String reserveRoom(
            @AuthenticationPrincipal SecurityUser securityUser,
            ReservationVO reservationVO
    ) {

    }
}
