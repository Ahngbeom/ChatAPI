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
		return "index";
	}
}
