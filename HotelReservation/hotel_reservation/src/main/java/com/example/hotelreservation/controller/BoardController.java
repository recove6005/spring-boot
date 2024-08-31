package com.example.hotelreservation.controller;

import com.example.hotelreservation.domains.security.SecurityUser;
import com.example.hotelreservation.domains.vo.BoardVO;
import com.example.hotelreservation.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// @PreAuthorize("hasRole('SELLER')")
@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping
    public void get_board() {

    }

    @PostMapping
    public String post_board(
            @AuthenticationPrincipal SecurityUser user,
            BoardVO boardVO
    ) {
        // 게시물 작성 시도
        if(boardService.post_board(user, boardVO)){
            // 성공시 => 메인화면으로
            return "redirect:/";
        }else{
            // 실패시 => 게시판으로 다시..
            return "redirect:/board?error";
        }
    }
}
