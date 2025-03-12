package com.recove6005.ticketingsystem.domain.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String userpw;
    private String userrole;
}
