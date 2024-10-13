package com.example.book.mapper.admin;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {
    String login(String id, String pw);
}