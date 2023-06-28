package com.sparta.blog_backend.user.contoriier;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {

    // 메인 페이지에 가기 위한 controller
    @GetMapping("/")
    public void home(Model model){
        model.addAttribute("username","username");
    }
}
