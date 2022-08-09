package com.example.chatapi.Controller;

import com.example.chatapi.DTO.UserDTO;
import com.example.chatapi.Entity.UserEntity;
import com.example.chatapi.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@Valid @RequestBody UserDTO userDTO) {
		UserEntity userEntity;
		try {
			log.info("Sign up: " + userDTO.getUsername());
			userEntity = userService.signUp(userDTO);
			return ResponseEntity.ok().body(userEntity);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
		}
	}

	@GetMapping("/user-info")
	public ResponseEntity<UserDTO> userInfo(Principal principal) {
		try {
			return ResponseEntity.ok(userService.getUserInfo(principal.getName()));
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.noContent().build();
		}
	}
}
