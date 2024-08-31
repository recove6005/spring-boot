package com.example.hotelreservation.controller;

import com.example.hotelreservation.domains.dto.ReservationDTO;
import com.example.hotelreservation.domains.security.SecurityUser;
import com.example.hotelreservation.domains.vo.ReservationVO;
import com.example.hotelreservation.domains.vo.UserVO;
import com.example.hotelreservation.mapper.ReserveMapper;
import com.example.hotelreservation.service.ReserveService;
import com.example.hotelreservation.service.SMSService;
import com.example.hotelreservation.service.UserMailService;
import com.example.hotelreservation.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
@Log4j2
public class UserController {
    @Autowired
    private SMSService smsService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMailService userMailService;
    @Autowired
    private ReserveService reserveService;

    @GetMapping("/login")
    public void get_login() {

    }

    @PostMapping("/login")
    public void post_login() {

    }

    /* 회원가입 */
    @GetMapping("/join")
    public void get_join() {

    }

    @PostMapping("/join")
    public String post_join(
            @Validated UserVO userVO, BindingResult result
    ) {
        System.out.println(userVO);
        if(result.hasErrors()) {
            log.error("USER_VO의 형식이 맞지 않음");
            log.info(userVO);
            return "redirect:/user/join";
        }
        userService.join_user(userVO);
        return "redirect:/"; // /home
    }

    @GetMapping("/mypage")
    public void get_mypage() {

    }

    @GetMapping("/mypage/info")
    public void get_mypage_info() {

    }

    @GetMapping("/mypage/reservation")
    public void get_mypage_reservation(
            @AuthenticationPrincipal SecurityUser user,
            Model model
    ){
        List<ReservationDTO> reservationDTOS = reserveService.get_reserve_info_by_user(user.getUserVO());
        model.addAttribute("ReservationDTOS", reservationDTOS);
    }

    @GetMapping("/mypage/sale")
    public void get_mypage_sale() {

    }

    @GetMapping("/mypage/heart")
    public void get_mypage_heart() {

    }


    // 인증번호 요청 시 인증번호 생성
    @GetMapping("/sms/key")
    @ResponseBody
    public boolean sms_test(HttpSession session, @RequestParam String telNum) {
        // 이미 한 번 인증 요청을 해서 인증번호를 받은 적이 있다면
        if(session.getAttribute("VERIFY_KEY") != null) {
            log.warn("이미 VERIFY_KEY가 존재하므로, 기존 코드를 삭제합니다");
            session.removeAttribute("VERIFY_KEY");
        }
        // 새로 VERIFY_KEY를 발급 받음
        String VERIFY_KEY = smsService.get_verify_key(telNum);
        // 인증키 발급 실패
        if(VERIFY_KEY == null) {
            // 인증키 발급 실패
            log.error("VERIFY_KEY가 생성되지 않았음 => SMS 요청 실패!");
            return false;
        } else {
            // 발급에 성공했다면
            log.info("VERIFY_KEY가 생성되었음 => " + VERIFY_KEY);
            session.setAttribute("VERIFY_KEY", VERIFY_KEY);
            return true;
        }
    }

    // 사용자가 인증번호를 작성하고 인증 시도
    @ResponseBody
    @GetMapping("/sms/verify")
    public boolean get_verify(HttpSession session, @RequestParam("key") String userKey) {
        Object object = session.getAttribute("VERIFY_KEY");
        // 인증번호를 발급받지 않고 session에서 인증번호를 가져오려 할 시 ERROR
        if(object == null) {
            log.error("생성되어있는 VERIFY_KEY가 존재하지 않음!");
            return false; //인증 실패!
        }

        String VERIFY_KEY = (String) object;
        log.info("생성되어있는 VERIFY_KEY => " + VERIFY_KEY);
        // 사용자가 입력한 값과, 기존 코드가 동일하다면
        if(VERIFY_KEY.equals(userKey)) {
            log.info("VERIFY_KEY가 일치함! 인증 성공!");
            session.setAttribute("verified", true);
            session.removeAttribute("VERIFY_KEY");
            return true;
        }
        // 사용자가 입력한 값이 옳지 않음
        return false;
    }


    // 아이디 비밀번호 찾기 페이지
    @GetMapping("/info")
    public void get_info() {

    }

    // 메일 발송하기
    @GetMapping("/find")
    @ResponseBody
    public void send_mail(
            @RequestParam String userEmail,
            @RequestParam(defaultValue = "") String userId
            ) {
        try {
            userMailService.find_id(userEmail);
        } catch (MessagingException e) {
            throw new RuntimeException();
        }
    }
}