package com.example.chatapi.Controller.API.Chat;

import com.example.chatapi.DTO.ChatRoomDTO;
import com.example.chatapi.STOMP.Message;
import com.example.chatapi.Service.Chat.ChatLogService;
import com.example.chatapi.Service.Chat.ChatRoomService;
import com.example.chatapi.Service.Account.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
@PreAuthorize("isAuthenticated()")
public class ChatAPIController {

    private final UserService userService;
    private final ChatRoomService chatRoomService;

    private final ChatLogService chatLogService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list/all")
    public ResponseEntity<List<ChatRoomDTO>> getListAllChatRooms() {
        return ResponseEntity.ok(chatRoomService.getListOfAllChatRooms());
    }

    @GetMapping("/list")
    public ResponseEntity<List<ChatRoomDTO>> getListAllChatRoomsByFounder(Principal principal, @RequestParam(required = false) String username) {
        return ResponseEntity.ok(chatRoomService.getListAllChatRoomsByFounder(username == null ? principal.getName() : username));
    }

    @PostMapping("/create")
    public ResponseEntity<ChatRoomDTO> createChatRoom(Principal principal, @RequestBody ChatRoomDTO chatRoom) {
        log.info(chatRoom.toString());
        ChatRoomDTO createdChatRoomDTO = chatRoomService.createChatRoom(principal.getName(), chatRoom);
        createdChatRoomDTO.setPermitMBTICode(chatRoomService.addPermitMBTICodes(createdChatRoomDTO.getRoomId(), chatRoom.getPermitMBTICode()));
        chatRoomService.joinChatRoom(createdChatRoomDTO.getRoomId(), principal.getName());
        chatLogService.saveMessage(createdChatRoomDTO.getRoomId(), new Message(Message.SERVER, Message.SERVER, "[" + principal.getName() + "] has opened a [" + createdChatRoomDTO.getRoomName() + "] chat room."));
        return ResponseEntity.status(HttpStatus.OK).body(createdChatRoomDTO);
    }

    @GetMapping("/info")
    public ResponseEntity<ChatRoomDTO> getInfoChatRoom(@RequestParam String roomName) {
        return ResponseEntity.ok(chatRoomService.getInfoChatRoom(roomName));
    }

    @PostMapping("/update")
    public ResponseEntity<ChatRoomDTO> updateChatRoom(Principal principal, @RequestBody ChatRoomDTO chatRoomDTO) {

        ChatRoomDTO updatedChatRoomDTO = chatRoomService.updateChatRoom(principal.getName(), chatRoomDTO);
        return ResponseEntity.status(updatedChatRoomDTO != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(updatedChatRoomDTO);

    }

    @GetMapping("/remove")
    public ResponseEntity<Boolean> removeChatRoom(Principal principal, @RequestParam(value = "roomId") Long roomId) {
        log.info(String.valueOf(roomId));
        if (!principal.getName().equals(chatRoomService.getInfoChatRoom(roomId).getFounder()))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(false);
        chatRoomService.removeChatRoom(roomId);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/join-availability/{roomId}")
    public ResponseEntity<String> joinChatRoomAvailability(@PathVariable Long roomId, Principal principal) {
        if (!chatRoomService.checkAlreadyJoined(roomId, principal.getName())) {
            if (!chatRoomService.joinChatRoomAvailability(roomId, principal.getName())) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Your MBTI code does not allow access to this chat room.");
            }
        } else {
            return ResponseEntity.ok("You're already in a chat room.");
        }
        return ResponseEntity.ok("Available");
    }

    @GetMapping("/leave-chatroom/{roomId}")
    public ResponseEntity<String> leaveChatRoom(@PathVariable Long roomId, Principal principal) {
        if (chatRoomService.leaveChatRoom(roomId, principal.getName())) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.unprocessableEntity().body("You need to transfer the founder privileges to other users.");
    }

}
