package com.example.chatapi.Controller.View;

import com.example.chatapi.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public ModelAndView userList(ModelAndView mv) {
        mv.addObject("userList", userService.getUserList());
        mv.setViewName("pages/user/userList");
        return mv;
    }
}
