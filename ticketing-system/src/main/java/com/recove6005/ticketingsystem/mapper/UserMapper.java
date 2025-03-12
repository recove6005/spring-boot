package com.recove6005.ticketingsystem.mapper;

import com.recove6005.ticketingsystem.domain.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    void insertUser(UserDTO user);
    UserDTO findByUsername(@Param("username") String username);
}
