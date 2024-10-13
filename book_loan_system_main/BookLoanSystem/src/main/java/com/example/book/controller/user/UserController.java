package com.example.book.controller.user;

import com.example.book.controller.user.domain.*;
import com.example.book.domain.vo.UserVO;
import com.example.book.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @GetMapping("join")
    public String join(@ModelAttribute("form") UserJoinForm form) {
        return "user/join";
    }

    @PostMapping("join")
    public String join(@Validated @ModelAttribute("form") UserJoinForm form, BindingResult br) {
        if (!form.getPw1().equals(form.getPw2()))
            br.rejectValue("pw2", "비밀번호와 일치하지 않습니다.");

        if (br.hasErrors())
            return "user/join";

        userService.join(form);

        return "redirect:/";
    }

    // 로그인
    @GetMapping("login")
    public String login(@ModelAttribute("form") UserLoginForm form,HttpSession session) {
        String userId = (String)session.getAttribute("userId");

        if(userId!=null)
            return "user/login-comp";


        return "user/login";
    }

    @PostMapping("login")
    public String login(@Validated @ModelAttribute("form") UserLoginForm form, BindingResult br, HttpSession session) {
        if (br.hasErrors())
            return "user/login";

        UserVO user = userService.login(form);

        if (user == null) {
            return "user/login-fail";
        } else {
            session.setAttribute("userId", user.getId());
        }

        return "redirect:/";
    }

    // 내 정보
    @GetMapping("my-page")
    public String myPage(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null)
            return "user/not-login";

        UserVO user = userService.userInfo(userId);
        model.addAttribute("user", user);
        return "user/my-page";
    }

    // 로그아웃
    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // 내 정보 수정
    @GetMapping("update")
    public String updateInfo(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if(userId==null)
            return "user/not-login";
        UserVO user = userService.userInfo(userId);
        UserUpdateForm form = new UserUpdateForm();

        form.setPw(user.getPw());
        form.setName(user.getName());
        form.setTel(user.getTel());
        form.setAddr(user.getAddr());

        model.addAttribute("form", form);
        return "user/update";
    }

    @PostMapping("update")
    public String updateInfo(@Validated @ModelAttribute("form") UserUpdateForm form,BindingResult br, HttpSession session){
        if(br.hasErrors())
            return "user/update";

        String userId = (String) session.getAttribute("userId");

        userService.update(userId, form);

        return "redirect:/user/my-page";
    }

    // 회원 탈퇴
    @GetMapping("delete")
    public String delete(HttpSession session){
        String userId = (String) session.getAttribute("userId");
        if(userId==null)
            return "user/not-login";

        userService.delete(userId);
        session.invalidate();
        return "/user/delete-user";
    }

    @GetMapping("info")
    public String info(@ModelAttribute("form")UserInfoForm form){
        return "user/my-page";
    }

    // 아이디 찾기
    @GetMapping("id-search")
    public String idSearch(Model model){
        model.addAttribute("form",new UserIdSearchForm());
        return "user/id-search";
    }

//    @PostMapping("id-search")
//    public String idSearch(){
//        return "";
//    }

    // 비밀번호 찾기
    @GetMapping("pw-search")
    public String pwSearch(Model model){
        model.addAttribute("form",new UserPwSearchForm());
        return "user/pw-search";
    }

//    @PostMapping("pw-search")
//    public String pwSearch(){
//        return "";
//    }
}
