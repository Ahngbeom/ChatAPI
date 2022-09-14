package com.example.chatapi.Controller.View;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/mbti")
public class MBTIController {

	@GetMapping({ "/list"})
	public String home(Principal principal) {
//		log.info(principal.getName());
		return "pages/mbti/mbtiList";
//		return "fragments/index";
//		return "fragments/default_layout";
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
