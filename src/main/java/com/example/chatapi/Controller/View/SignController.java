package com.example.chatapi.Controller.View;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Slf4j
@Controller
public class SignController {

    @GetMapping("/login")
    public String login(Principal principal, String logout, String error) {
//        log.info(logout);
//        log.info(error);
        if (principal != null)
            return "redirect:/mbti";
        return "user/login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "user/signup";
    }

}
