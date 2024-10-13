//package com.example.book.Interceptor;
//
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//public class LoginInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpSession session = request.getSession();
//        Long memberId = (Long) session.getAttribute("memberId");
//        Long teacherId = (Long) session.getAttribute("teacherId");
//        Long adminId = (Long) session.getAttribute("adminId");
//        if(memberId==null&&teacherId==null&&adminId==null){
//            response.sendRedirect("/member/login");
//            return false;
//        }
//        return true;
//    }
//}
