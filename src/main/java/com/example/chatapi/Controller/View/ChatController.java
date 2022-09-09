package com.example.chatapi.Controller.View;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ChatController {

    @GetMapping({"/chat"})
    public String chatGet() {
//        log.info("@ChatController, chat GET()");
        return "chat/chat";
    }

    @GetMapping({"/chat-list"})
    public String chatListGet() {
//        log.info("@ChatController, chat GET()");
        return "chat/chat-list";
    }
}
