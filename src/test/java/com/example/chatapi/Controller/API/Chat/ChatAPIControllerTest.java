package com.example.chatapi.Controller.API.Chat;

import com.example.chatapi.ChatApplicationIntegrationTests;
import com.example.chatapi.DTO.ChatRoomDTO;
import com.example.chatapi.DTO.MBTICode;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

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
    void createChatRoom() throws Exception {
        ChatRoomDTO chatRoomDTO = new ChatRoomDTO("TEST ROOM FOR ALL OF US", "For Testing", MBTICode.ALL);

        MvcResult mvcResult = mvc.perform(post("/api/chat/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(chatRoomDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        log.info(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    void getInfoChatRoom() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/api/chat/info")
                        .param("roomName", "ISFJ의 방")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        log.info(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    void updateChatRoom() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/api/chat/info")
                        .param("roomName", "JUNIT TEST ROOM")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        log.info(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));

        ChatRoomDTO existChatRoom = objectMapper.readValue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), ChatRoomDTO.class);
        log.info("Origin: " + existChatRoom.toString());

        existChatRoom.setRoomName("JUNIT TEST ROOM JUNIT TEST ROOM");
        mvcResult = mvc.perform(post("/api/chat/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(existChatRoom))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        log.info("Updated: " + mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    void removeChatRoom() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/api/chat/info")
                        .param("roomName", "JUNIT TEST ROOM")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Long roomId = objectMapper.readValue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), ChatRoomDTO.class).getId();
        mvcResult = mvc.perform(get("/api/chat/remove")
                        .param("roomId", roomId.toString()))
                .andExpect(status().isOk())
                .andReturn();

        log.info("Remove: " + (Boolean.parseBoolean(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8)) ? "SUCCESS" : "FAILURE"));

    }

    @Test
    void joinChatRoomAvailability() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/api/chat/info")
                        .param("roomName", "JUNIT TEST ROOM")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Long roomId = objectMapper.readValue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), ChatRoomDTO.class).getId();
        mvcResult = mvc.perform(get("/api/chat/join-availability/" + roomId))
                .andExpect(status().isOk())
                .andReturn();

        log.info("Join Chat Room: " + mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    void leaveChatRoom() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/api/chat/info")
                        .param("roomName", "JUNIT TEST ROOM")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Long roomId = objectMapper.readValue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), ChatRoomDTO.class).getId();
        mvcResult = mvc.perform(get("/api/chat/leave-chatroom/" + roomId))
                .andExpect(status().isOk())
                .andReturn();

        log.info("Leave Chat Room: " + mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }
}