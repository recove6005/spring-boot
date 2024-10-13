package com.example.book.mapper.book;

import com.example.book.domain.vo.ReservationVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReservationMapper {

    ReservationVO get_reservation_by_no(int no);
    List<ReservationVO> get_reservation_by_user(String userId);
    List<ReservationVO> get_reservation_by_book(int bookNo);
    void save(ReservationVO reservationVO);
    void update(int no, ReservationVO reservationVO);
    void remove(int no);
}