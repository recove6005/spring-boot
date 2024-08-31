package com.example.hotelreservation.mapper;

import com.example.hotelreservation.domains.vo.BoardVO;
import com.example.hotelreservation.domains.vo.RoomImagesVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    void post_board(BoardVO boardVO);
    void post_images(List<RoomImagesVO> roomImagsVO);
}