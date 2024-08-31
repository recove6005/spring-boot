package com.example.hotelreservation.domains.security;

import com.example.hotelreservation.domains.vo.UserVO;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

@Getter @Setter
@ToString
public class SecurityUser extends User {
    private final UserVO userVO;

    public SecurityUser(UserVO userVO, Collection<? extends GrantedAuthority> authorities) {
        super(userVO.getId(), userVO.getPw(), authorities);
        this.userVO = userVO;
    }
}
