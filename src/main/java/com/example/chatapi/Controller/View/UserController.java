package com.example.chatapi.Controller.View;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public String userList() {
        return "pages/user/userList";
    }
}
