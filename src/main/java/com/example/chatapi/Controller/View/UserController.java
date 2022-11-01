package com.example.chatapi.Controller.View;

import com.example.chatapi.Service.Account.OAuth2UserService;
import com.example.chatapi.Service.Account.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class UserController {

    private final UserService userService;

    private final OAuth2UserService oAuth2UserService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public ModelAndView userListPage(ModelAndView mv) {
        mv.addObject("userList", userService.getUserList());
        mv.setViewName("pages/user/userList");
        return mv;
    }

    @GetMapping("/info")
    public ModelAndView userInfoPage(ModelAndView mv, Principal principal) {
        try {
            mv.addObject("userInfo", userService.getUserInfo(principal.getName()));
        } catch (UsernameNotFoundException notFoundException) {
            try {
                mv.addObject("userInfo", oAuth2UserService.getOAuth2UserInfo(principal.getName()));
            } catch (UsernameNotFoundException notFoundException1) {
                notFoundException.printStackTrace();
                notFoundException1.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mv.setViewName("pages/user/info");
        return mv;
    }

    @GetMapping("/update")
    public ModelAndView userUpdatePage(ModelAndView mv, Principal principal) {
        mv.addObject("userInfo", userService.getUserInfo(principal.getName()));
        mv.setViewName("pages/user/update");
        return mv;
    }
}
