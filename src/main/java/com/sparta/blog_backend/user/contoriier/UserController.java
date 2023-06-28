package com.sparta.blog_backend.user.contoriier;

import com.sparta.blog_backend.user.dto.LoginRequestDto;
import com.sparta.blog_backend.user.dto.SignupRequestDto;
import com.sparta.blog_backend.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/user/login-page")
//    public String loginPage() {
//        return "login"; // => html 없음
//    }
//
//    @GetMapping("/user/signup")
//    public String signupPage() {
//        return "signup"; // => html 없음
//    }

    // 회원 가입
    @PostMapping("/user/signup")
    public String signup(SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return "redirect:/api/user/login-page"; // =>  HTML 없음
    }

    // 로그인
    @PostMapping("/user/login")
    // HttpServletResponse : WAS가 클라이언트로부터 Servlet으로 요청을 받을 경우 생성하여 사용
    // => 검증용
    public String login(LoginRequestDto requestDto, HttpServletResponse res){
        try {
            userService.login(requestDto, res);
        } catch (Exception e) {
            return "redirect:/api/user/login-page"; // => 오류 발생시 로그인 페이지로 이동
        }

        return "redirect:/"; // =>  로그인 성공햐면 홈 페이지로 이동
    }


}
