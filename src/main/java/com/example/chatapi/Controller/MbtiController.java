package com.example.chatapi.Controller;

import com.example.chatapi.DTO.MbtiDTO;
import com.example.chatapi.Service.MbtiService;
import com.example.chatapi.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mbti")
@RequiredArgsConstructor
public class MbtiController {

	private final UserService userService;
	private final MbtiService mbtiService;

	@PostAuthorize("isAuthenticated()")
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

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/list")
	public ResponseEntity<List<MbtiDTO>> getList(Principal principal, @RequestParam(required = false) Long userNo) {
		try {
			log.info(principal.getName());
			if (userNo == null) {
				userNo = userService.getUserInfo(principal.getName()).getId();
			}
			List<MbtiDTO> list = mbtiService.getUserMbtiList(userNo);
			list.forEach(mbtiDTO -> log.info(mbtiDTO.getCode()));
			return ResponseEntity.ok().body(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
	}
}
