package com.example.chatapi.Controller.API;

import com.example.chatapi.DTO.MbtiDTO;
import com.example.chatapi.Service.MBTI.MbtiService;
import com.example.chatapi.Service.Account.UserService;
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

    @GetMapping("/info/{code}")
    public ResponseEntity<MbtiDTO> mbtiInfo(@PathVariable String code) {
        log.info(code);
        return ResponseEntity.ok(mbtiService.getInfo(code));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> mbtiRegister(Principal principal, @Valid @RequestBody MbtiDTO mbtiDTO) {
        try {
//			log.info(userService.getUserInfo(principal.getName()).toString());
//			log.warn(mbtiDTO.toString());

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
            if (username == null) {
                username = principal.getName();
            }
            List<MbtiDTO> list = mbtiService.getUserMbtiList(username);
            return ResponseEntity.ok().body(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
    }

    @GetMapping("/get-represent")
    public ResponseEntity<MbtiDTO> getRepresentMBTI(Principal principal, @RequestParam(required = false) String username) {
        return ResponseEntity.ok(
                username != null
                        ? mbtiService.getRepresentMBTI(username)
                        : mbtiService.getRepresentMBTI(principal.getName()));
    }

    @GetMapping("/assign-represent")
    public ResponseEntity<Boolean> assignRepresentMBTI(Principal principal, @RequestParam String mbtiCode) {
        mbtiService.assignRepresentMBTI(principal.getName(), mbtiCode);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/release-represent")
    public ResponseEntity<Boolean> releaseRepresentMBTI(Principal principal) {
        mbtiService.releaseRepresentMBTI(principal.getName());
        return ResponseEntity.ok(true);
    }
}
