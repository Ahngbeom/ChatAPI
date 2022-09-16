package com.example.chatapi.Controller.View;

import com.example.chatapi.Service.MbtiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/mbti")
@RequiredArgsConstructor
public class MBTIController {

	private final MbtiService mbtiService;

	@GetMapping({ "/list"})
	public ModelAndView myMbtiList(ModelAndView mv, Principal principal) {

		mv.addObject("MBTI_List", mbtiService.getUserMbtiList(principal.getName()));
//		log.info(mbtiService.getUserMbtiList(principal.getName()).toString());
		mv.setViewName("pages/mbti/mbtiList");
		return mv;
	}

	@GetMapping("/register")
	public String register() {
		return "pages/mbti/register";
	}

	@GetMapping("/test")
	public String test() {
		return "test_page";
	}

}
