package com.example.chatapi.Controller.View;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Slf4j
@Controller
public class SignController {

    @GetMapping("/login")
    public ModelAndView login(ModelAndView mv, Principal principal, String logout, String error) {
        log.info("logout: " + logout);
        log.info("error: " + error);
//        if (principal != null)
//            mv.setViewName("redirect:/");
        if (logout != null) {
            mv.addObject("server_message", "로그아웃되었습니다.");
        }
        if (error != null) {
            mv.addObject("server_message", "다시 로그인해주세요.");
        }
        mv.setViewName("pages/user/login");
        return mv;
    }

    @GetMapping("/signup")
    public ModelAndView signup(ModelAndView mv) {
        mv.setViewName("pages/user/signup");
        return mv;
    }

}
