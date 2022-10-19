package com.example.chatapi.Controller.View;

import com.example.chatapi.Service.MBTI.MbtiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MbtiService mbtiService;

    @GetMapping("/")
    public ModelAndView home(ModelAndView mv) {
        mv.addObject("mbtiInfoList", mbtiService.getAllMbtiList());
        mv.setViewName("pages/index");
        return mv;
    }
}
