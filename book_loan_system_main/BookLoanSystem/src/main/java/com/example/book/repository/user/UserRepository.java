package com.example.book.repository.user;

import com.example.book.controller.user.domain.UserUpdateForm;
import com.example.book.domain.vo.UserVO;
import com.example.book.mapper.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @Autowired
    UserMapper userMapper;

    public void save(UserVO user) {
        userMapper.save(user);
    }

    public UserVO findByIdAndPw(String id, String pw) {
        return userMapper.find_by_id_and_pw(id, pw);
    }

    public void update(String userId, UserUpdateForm form) {
        userMapper.update(userId, form);
    }

    public void delete(String userId) {
        userMapper.delete(userId);
    }

    public UserVO findById(String userId){
        return userMapper.find_by_id(userId);
    }
}