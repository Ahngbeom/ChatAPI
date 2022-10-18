package com.example.chatapi.Controller.API;

import com.example.chatapi.ChatApplicationIntegrationTests;
import com.example.chatapi.DTO.MBTICode;
import com.example.chatapi.DTO.MbtiDTO;
import com.example.chatapi.Repository.MbtiRepository;
import com.example.chatapi.Security.CustomUserDetailService;
import com.example.chatapi.Service.MbtiServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MbtiAPIControllerTest extends ChatApplicationIntegrationTests {

    @InjectMocks
    @Autowired
    private MbtiServiceImpl mbtiService;

    @Mock
    @Autowired
    private MbtiRepository mbtiRepository;

    @Test
    @WithUserDetails("admin")
    void mbtiRegister() throws Exception {
        // Given
        MbtiDTO mbtiDTO = mbtiService.getInfo(MBTICode.ISTJ.name());

        // When
        ResultActions resultActions = mvc.perform(post("/api/mbti/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mbtiDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("admin")
    void getListByAdmin() throws Exception {
        // When
        ResultActions resultActions = mvc.perform(get("/api/mbti/list")
                        .param("username", "manager")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("admin")
    void getRepresentMBTI() throws Exception {

        // When
        MvcResult mvcResult = mvc.perform(get("/api/mbti/get-represent")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @WithUserDetails("admin")
    void assignRepresentMBTI() throws Exception {
        // When
        MvcResult mvcResult = mvc.perform(get("/api/mbti/assign-represent")
                        .param("mbtiCode", MBTICode.INTP.name())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @WithUserDetails("admin")
    void releaseRepresentMBTI() throws Exception {
        // When
        MvcResult mvcResult = mvc.perform(get("/api/mbti/release-represent")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        log.info(mvcResult.getResponse().getContentAsString());
    }
}