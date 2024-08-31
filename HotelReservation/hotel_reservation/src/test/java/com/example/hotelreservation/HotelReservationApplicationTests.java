package com.example.hotelreservation;

import com.example.hotelreservation.mapper.RoomMapper;
import com.example.hotelreservation.mapper.UserMapper;
import com.example.hotelreservation.service.SMSService;
import com.example.hotelreservation.service.UserMailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;

import java.io.UnsupportedEncodingException;

import static java.lang.System.out;
import static java.lang.System.setOut;

@SpringBootTest
class HotelReservationApplicationTests {
    @Autowired
    RoomMapper roomMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    SMSService smsService;

    @Autowired
    UserMailService userMailService;

    @Test
    void contextLoads() throws MessagingException, UnsupportedEncodingException {

        // System.out.println(roomMapper.get_all_rooms());
        //System.out.println(userMapper.get_user("seluser"));

        smsService.send_sms("01085057657");

        // userMailService.send_mail();
    }

}
