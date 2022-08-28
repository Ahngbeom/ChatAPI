package com.example.chatapi.Controller;

import com.example.chatapi.DTO.MbtiDTO;
import com.example.chatapi.Entity.MBTIInfoEntity;
import com.example.chatapi.Service.MbtiService;
import com.example.chatapi.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/mbti")
@RequiredArgsConstructor
public class MbtiRestController {

	private final UserService userService;
	private final MbtiService mbtiService;

	@PostMapping("/registration")
	public ResponseEntity<?> mbtiRegister(Principal principal, @Valid @RequestBody MbtiDTO mbtiDTO) {
		try {
//			log.info(userService.getUserInfo(principal.getName()).toString());
//			log.warn(mbtiDTO.getMbti());
//			log.warn(mbtiDTO.getPersonality());
//			log.warn(mbtiDTO.getIntroduction());

			MBTIInfoEntity mbtiInfoEntity = mbtiService.register(mbtiDTO);

//			userEntity.getMbtiList().forEach(element -> {
//				log.info(element.getMbti() + ", " + element.getPersonality() + ", " + element.getIntroduction());
//			});
			return ResponseEntity.ok(userService.addMbti(principal.getName(), mbtiInfoEntity));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/list")
	public ResponseEntity<Set<MBTIInfoEntity>> getList(Principal principal) {
		try {
			log.info(principal.getName());
			Set<MBTIInfoEntity> list = userService.getMbtiList(principal.getName());
			list.forEach(mbtiInfoEntity -> log.info(mbtiInfoEntity.getMbti()));
			return ResponseEntity.ok().body(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
	}
}
