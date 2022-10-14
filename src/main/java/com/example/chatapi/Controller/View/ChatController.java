package com.example.chatapi.Controller.View;

import com.example.chatapi.Service.Chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping({"/"})
    public String chatGet() {
//        log.info("@ChatController, chat GET()");
        return "pages/chat/chat";
    }

    @GetMapping({"/list"})
    public ModelAndView myChatRoomList(ModelAndView mv, Principal principal, @RequestParam(required = false) String founder, @RequestParam(required = false) String belongs) {
        if (founder != null) {
            mv.addObject("chatRoomList", chatService.getListOfAllChatRoomsUserHasJoined(principal.getName()));
        } else if (belongs != null) {
            mv.addObject("chatRoomList", chatService.getListOfAllChatRoomsUserBelongs(principal.getName()));
        } else {
            mv.addObject("chatRoomList", chatService.getListOfAllChatRooms());
        }
        mv.setViewName("pages/chat/chatList");
        return mv;
    }
}
