package com.recove6005.room_reservation.mapper;


import com.recove6005.room_reservation.domains.dto.RoomDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoomMapper {
    List<RoomDTO> getAllRooms(Integer roomNo);
}
