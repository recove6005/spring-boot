package com.example.hotelreservation.service.security;

import com.example.hotelreservation.domains.security.SecurityUser;
import com.example.hotelreservation.domains.vo.UserVO;
import com.example.hotelreservation.mapper.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(" ======= loadUserByUsername: ["+ username + "]");

            UserVO userVO = userMapper.get_user_by_id(username);

            // 유저 조회 실패
            if(userVO == null) {
                throw new UsernameNotFoundException("ERROR: USER NOT FOUND!");
            }

            // 유저 조회 성공
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + userVO.getRole());
        // SecurityUser securityUser = SecurityUser.builder().userVO(userVO).build();
            SecurityUser securityUser = new SecurityUser(userVO, List.of(authority));
            return securityUser;
        }
}