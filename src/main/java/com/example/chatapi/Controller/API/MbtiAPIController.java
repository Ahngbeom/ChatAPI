package com.example.chatapi.Controller.API;

import com.example.chatapi.DTO.MbtiDTO;
import com.example.chatapi.Service.MbtiService;
import com.example.chatapi.Service.User.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/mbti")
@RequiredArgsConstructor
@PostAuthorize("isAuthenticated()")
public class MbtiAPIController {

	private final UserService userService;
	private final MbtiService mbtiService;

	@PostMapping("/registration")
	public ResponseEntity<?> mbtiRegister(Principal principal, @Valid @RequestBody MbtiDTO mbtiDTO) {
		try {
			log.info(userService.getUserInfo(principal.getName()).toString());
			log.warn(mbtiDTO.toString());

//			userEntity.getMbtiList().forEach(element -> {
//				log.info(element.getMbti() + ", " + element.getPersonality() + ", " + element.getIntroduction());
//			});
			return ResponseEntity.ok(mbtiService.addMbti(userService.getUserInfo(principal.getName()), mbtiDTO));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/list")
	public ResponseEntity<List<MbtiDTO>> getList(Principal principal, @RequestParam(required = false) String username) {
		try {
			log.info(principal.getName());
			if (username == null) {
				username = principal.getName();
			}
			List<MbtiDTO> list = mbtiService.getUserMbtiList(username);
			list.forEach(mbtiDTO -> log.info(mbtiDTO.getCode()));
			return ResponseEntity.ok().body(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
	}

	@GetMapping("/select-represent")
	public ResponseEntity<Boolean> selectRepresentMBTI(Principal principal, @RequestParam String mbtiCode) {
		mbtiService.assignRepresentMBTI(principal.getName(), mbtiCode);
		return ResponseEntity.ok(true);
	}
}
