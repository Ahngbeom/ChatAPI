package com.example.chatapi.Controller.View;

import com.example.chatapi.Service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/info")
    public ModelAndView userInfo(ModelAndView mv, Principal principal) {
        mv.addObject("userInfo", userService.getUserInfo(principal.getName()));
        mv.setViewName("pages/user/info");
        return mv;
    }
}
