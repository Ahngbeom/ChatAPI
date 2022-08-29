package com.example.chatapi.Controller.View;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class SignController {

    @GetMapping("/login")
    public String login(String logout, String error) {
//        log.info(logout);
//        log.info(error);
        return "user/login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "user/signup";
    }

}
