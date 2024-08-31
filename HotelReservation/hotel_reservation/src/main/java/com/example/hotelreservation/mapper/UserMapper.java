package com.example.hotelreservation.mapper;

import com.example.hotelreservation.domains.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    // 로그인 시 id로 유저 조회
    UserVO get_user_by_id(String id);
    // 아이디 찾기 시 email로 유저 조회
    UserVO get_user_by_email(String email);
    // 회원가입
    void join_user(UserVO vo);

    // 마이페이지에서의 유저 정보 조회
    // void get_user();
}