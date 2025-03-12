package com.recove6005.ticketingsystem.service;

import com.recove6005.ticketingsystem.domain.dto.UserDTO;
import com.recove6005.ticketingsystem.mapper.UserMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public void signup(String username, String password) {
        UserDTO user = new UserDTO();
        user.setUsername(username);
        user.setUserpw(bCryptPasswordEncoder.encode(password)); // 암호화 비밀번호
        user.setUserrole("USER");
        userMapper.insertUser(user);
    }

    public UserDTO findUserByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
