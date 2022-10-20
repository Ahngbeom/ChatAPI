package com.example.chatapi.Controller.View;

import com.example.chatapi.DTO.MbtiDTO;
import com.example.chatapi.Service.MBTI.MbtiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/mbti")
@RequiredArgsConstructor
public class MBTIController {

	private final MbtiService mbtiService;

	@GetMapping({ "/list"})
	public ModelAndView myMbtiList(ModelAndView mv, Principal principal) {

		List<MbtiDTO> mbtiDTOList = mbtiService.getUserMbtiList(principal.getName());
		mv.addObject("MBTI_List", mbtiDTOList);
		mv.addObject("totalNumberOfTests", mbtiDTOList.stream().map(MbtiDTO::getNumberOfTimes).reduce(0, Integer::sum));
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
