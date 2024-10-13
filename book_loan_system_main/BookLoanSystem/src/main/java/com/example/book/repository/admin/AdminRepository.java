package com.example.book.repository.admin;

import com.example.book.mapper.admin.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// 관리자 저장소 조회(로그인)
@Repository
public class AdminRepository {
    @Autowired
    AdminMapper adminMapper;

    public String login(String id, String pw) {
        // id,pw가 일치하는 회원정보가 없을 시 null 값 반환
        return adminMapper.login(id, pw);
    }
}
