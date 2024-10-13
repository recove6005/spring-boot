package com.example.book.repository.book;

import com.example.book.domain.vo.ReservationVO;
import com.example.book.mapper.book.ReservationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

// 예약 저장소
// 등록, 수정, 삭제, 조회(단 건, 회원별, 도서별)
@Repository
public class ReservationRepository {
    @Autowired
    ReservationMapper reservationMapper;

    // 단 건 조회
    public ReservationVO get_reservation_by_no(int no) {
        return reservationMapper.get_reservation_by_no(no);
    }

    // 회원별 조회
    public List<ReservationVO> get_reservation_by_user(String userId) {
        return reservationMapper.get_reservation_by_user(userId);
    }

    // 도서별 조회
    public List<ReservationVO> get_reservation_by_book(int bookNo) {
        return reservationMapper.get_reservation_by_book(bookNo);
    }

    // 등록
    public void save(ReservationVO reservation) {
        reservationMapper.save(reservation);
    }

    // 수정
    public void update(int no, ReservationVO reservationVO) {
        reservationMapper.update(no, reservationVO);
    }

    // 삭제
    public void remove(int reservationNo) {
        reservationMapper.remove(reservationNo);
    }
}