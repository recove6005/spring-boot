package com.example.book;

import com.example.book.domain.vo.BookVO;
import com.example.book.domain.vo.ReservationVO;
import com.example.book.mapper.admin.AdminMapper;
import com.example.book.mapper.book.BookMapper;
import com.example.book.mapper.book.ReservationMapper;
import com.example.book.mapper.user.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class BookLoanSystemApplicationTests {
    @Autowired
    UserMapper userMapper;

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    ReservationMapper reservationMapper;

    @Test
    void contextLoads() {
        System.out.println(reservationMapper.get_reservation_by_book(2));
        ReservationVO reserv = new ReservationVO();
        reserv.setNo(2);
        reserv.setBookNo(2);
        reserv.setUserId("dum3");
        reserv.setDate(LocalDateTime.now());
        reservationMapper.update(2, reserv);
    }
}