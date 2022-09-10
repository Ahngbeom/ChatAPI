package com.example.chatapi.Controller.View;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Slf4j
@Controller
public class MBTIController {

	@GetMapping({"/", "/mbti"})
	public String home(Principal principal) {
//		log.info(principal.getName());
		return "pages/index";
//		return "fragments/index";
//		return "fragments/default_layout";
	}

	@GetMapping("/mbti/register")
	public String register() {
		return "pages/mbti/register";
	}

	@GetMapping("/test")
	public String test() {
		return "test";
	}

}
