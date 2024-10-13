package com.example.book.mapper.user;

import com.example.book.controller.user.domain.UserLoginForm;
import com.example.book.controller.user.domain.UserUpdateForm;
import com.example.book.domain.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void save(UserVO userVO);
    UserVO find_by_id_and_pw(String id, String pw);
    void update(String id, UserUpdateForm user);
    void delete(String id);
    UserVO find_by_id(String id);
}
