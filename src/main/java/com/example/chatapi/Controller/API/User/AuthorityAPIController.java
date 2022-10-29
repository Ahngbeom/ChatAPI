package com.example.chatapi.Controller.API.User;

import com.example.chatapi.DTO.AuthorityDTO;
import com.example.chatapi.Service.Account.AuthorityService;
import com.example.chatapi.Service.Account.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/user/authorities")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class AuthorityAPIController {

    private final AuthorityService authorityService;

    @GetMapping("/")
    public ResponseEntity<List<AuthorityDTO>> getMyAuthorities(Authentication authentication) {
        try {
            return ResponseEntity.ok(authentication.getAuthorities().stream()
                    .map(grantedAuthority
                            -> AuthorityDTO.builder().authorityName(grantedAuthority.getAuthority()).build())
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/{username}")
    public ResponseEntity<List<AuthorityDTO>> getUserAuthoritiesByAdmin(@PathVariable String username) {
        try {
            return ResponseEntity.ok(authorityService.getUserAuthorities(username));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/update/{username}")
    public ResponseEntity<?> updateUserAuthorities(@PathVariable String username, @RequestParam(value = "authorities[]") List<String> authorities) {
        try {
            authorityService.updateAuthority(username, authorities);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);
        }
        return ResponseEntity.ok(null);
    }
}
