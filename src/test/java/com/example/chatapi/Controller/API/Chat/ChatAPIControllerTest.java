package com.example.chatapi.Controller.API.Chat;

import com.example.chatapi.ChatApplicationIntegrationTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails("admin")
class ChatAPIControllerTest extends ChatApplicationIntegrationTests {

    @Test
    void getListOfAllChatRooms() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/api/chat/list/all"))
                .andExpect(status().isOk())
                .andReturn();

        log.info(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    void getListAllChatRoomsByFounder() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/api/chat/list"))
                .andExpect(status().isOk())
                .andReturn();

        log.info(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    void createChatRoom() {
    }

    @Test
    void getInfoChatRoom() {
    }

    @Test
    void updateChatRoom() {
    }

    @Test
    void removeChatRoom() {
    }

    @Test
    void joinChatRoomAvailability() {
    }

    @Test
    void leaveChatRoom() {
    }
}