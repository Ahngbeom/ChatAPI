package com.example.chatapi.Controller.API;

import com.example.chatapi.ChatApplicationIntegrationTests;
import com.example.chatapi.DTO.MBTICode;
import com.example.chatapi.DTO.MbtiDTO;
import com.example.chatapi.Repository.MbtiRepository;
import com.example.chatapi.Service.MbtiServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
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

//    @Mock
//    @Autowired
//    private MbtiDTO mbtiDTO;

    @Test
    @WithMockUser(username = "tester")
    void mbtiRegister() throws Exception {
        // Given
        MbtiDTO mbtiDTO = mbtiService.getInfo(MBTICode.ISTJ.name());

//        // When
//        ResultActions resultActions = mvc.perform(post("/api/mbti/registration")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(mbtiDTO))
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print());
//
//        // Then
//        resultActions
//                .andExpect(status().isOk());
    }

    @Test
    void getList() {
    }

    @Test
    void selectRepresentMBTI() {
    }
}