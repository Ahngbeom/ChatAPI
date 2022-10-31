package com.example.chatapi.Controller.API.User;

import com.example.chatapi.DTO.UserDTO;
import com.example.chatapi.Service.Account.AuthorityService;
import com.example.chatapi.Service.Account.OAuth2UserService;
import com.example.chatapi.Service.Account.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserAPIController {

	private final UserService userService;

	private final OAuth2UserService oAuth2UserService;
	private final AuthorityService authorityService;

	List<String> forbiddenWords = new ArrayList<>(Arrays.asList("administrator", "admin", "manager", "user", "server", "test", "tester"));

	@GetMapping("/info")
	public ResponseEntity<?> userInfo(Principal principal) {
		try {
			if (principal == null)
				throw new AuthenticationException("Not Logged In");
			return ResponseEntity.ok(userService.getUserInfo(principal.getName()));
		} catch (UsernameNotFoundException notFoundException) {
			// Finding OAuth2 User DB
			try {
				assert principal != null;
				return ResponseEntity.ok(oAuth2UserService.getOAuth2UserInfo(principal.getName()));
			} catch (Exception e) {
				e.printStackTrace();
				log.error(notFoundException.getMessage());
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(notFoundException.getMessage());
			}

		} catch (AuthenticationException authenticationException) {
			log.error(authenticationException.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authenticationException.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@Valid @RequestBody UserDTO userDTO) {
		try {
//			log.info("=== User information to be registered to register as a member ===");
//			log.info("User: " + userDTO.toString());
//			log.info("User's Authority: " + userDTO.getAuthorities());
			userService.signUp(userDTO);
			authorityService.addAuthority(userDTO.getUsername(), userDTO.getAuthorities());
			return ResponseEntity.ok().body(null);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
		}
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
	@GetMapping("/list")
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

	@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
	@GetMapping("/remove/{username}")
	public ResponseEntity<String> removeUser(@PathVariable String username) {
		try {
			userService.removeUser(username);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.ok(null);
	}

	@GetMapping("/username-validation")
	public ResponseEntity<?> usernameValidation(@RequestParam String username) throws RuntimeException {
		try {
			forbiddenWords.forEach(word -> {
				if (username.toLowerCase().contains(word))
					throw new RuntimeException("Username contains forbidden words - \"" + word + "\"");
			});
			userService.getUserInfo(username);
		} catch (UsernameNotFoundException e) {
//			e.printStackTrace();
//			log.info("Username \"" + username + "\" is " + e.getMessage() + " (" + e.getClass() + ")");
			return ResponseEntity.ok(true);
		} catch (RuntimeException forbidden) {
//			forbidden.printStackTrace();
//			log.error(forbidden.getMessage());
			return ResponseEntity.ok(forbidden.getMessage());
		}
		log.error("Username \"" + username + "\" already exists");
		return ResponseEntity.ok("Username \"" + username + "\" already exists");
	}

	@GetMapping("/nickname-validation")
	public Boolean nicknameValidation(@RequestParam String nickname) {
		try {
			forbiddenWords.forEach(word -> {
				if (nickname.toLowerCase().contains(word))
					throw new RuntimeException("Nickname contains forbidden words - \"" + word + "\"");
			});
			return userService.nicknameValidation(nickname);
		} catch (Exception e) {
//			e.printStackTrace();
			log.info("Nickname \"" + nickname + "\" is " + e.getMessage() + " (" + e.getClass() + ")");
		}
		return false;
	}
}
