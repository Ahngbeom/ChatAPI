package com.example.chatapi.Controller;

import com.example.chatapi.DTO.UserDTO;
import com.example.chatapi.Entity.UserEntity;
import com.example.chatapi.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

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

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/user-info")
	public ResponseEntity<UserDTO> userInfo(Principal principal) {
		try {
			return ResponseEntity.ok(userService.getUserInfo(principal.getName()));
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/user-list")
	public ResponseEntity<List<UserDTO>> userList() {
		try {
			List<UserDTO> userDTOList = userService.getUserList();
//			for (UserDTO user : userDTOList) {
//				log.info(user.getUsername());
//			}
			return ResponseEntity.ok(userDTOList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
	}

	@GetMapping("/username-validation")
	public Boolean usernameValidation(@RequestParam String username) {
		try {
			userService.getUserInfo(username);
		} catch (Exception e) {
//			e.printStackTrace();
			log.info("Username \"" + username + "\" is " + e.getMessage() + " (" + e.getClass() + ")");
			return true;
		}
		log.info("Username \"" + username + "\" already exists");
		return false;
	}

	@GetMapping("/nickname-validation")
	public Boolean nicknameValidation(@RequestParam String nickname) {
		try {
			return userService.nicknameValidation(nickname);
		} catch (Exception e) {
//			e.printStackTrace();
			log.info("Nickname \"" + nickname + "\" is " + e.getMessage() + " (" + e.getClass() + ")");
		}
		return false;
	}
}
