package com.example.chatapi.Controller.API;

import com.example.chatapi.ChatApplicationIntegrationTests;
import com.example.chatapi.DTO.AuthorityDTO;
import com.example.chatapi.DTO.UserDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserAPIControllerTest extends ChatApplicationIntegrationTests {

    private static final Logger log = LoggerFactory.getLogger(ChatApplicationIntegrationTests.class);

    @BeforeEach
    void setUp() {
    }

    @Test
    void signUp() throws Exception {

        // Given
        UserDTO createUser = UserDTO.builder()
                .username("TESTER")
                .password("TESTER1234")
                .nickname("TESTER")
                .authorities(AuthorityDTO.authoritiesToSet(AuthorityDTO.USER))
                .build();

        // When
        ResultActions resultActions = mvc.perform(post("/api/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUser))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("username", is("TESTER")))
                .andExpect(jsonPath("regDate", is(notNullValue())))
//                .andExpect(jsonPath("$.authorities[*].authorityName", Matchers.contains("ROLE_ADMIN")))
                .andExpect(jsonPath("$.authorities[*].authorityName", Matchers.contains("ROLE_USER")));
    }

    @Test
    @WithMockUser(username = "admin"/*, password = "admin", roles = {"ADMIN"}, authorities = {"ADMIN"}*/)
        // Given
    void userInfo() throws Exception {

        // When
        ResultActions resultActions = mvc.perform(get("/api/user/info")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void userListByAdmin() throws Exception {
        // When
        ResultActions resultActions = mvc.perform(get("/api/user/list")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "manager", roles = {"MANAGER"})
    void userListByManager() throws Exception {
        // When
        ResultActions resultActions = mvc.perform(get("/api/user/user-list")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "tester01", roles = {"USER"})
    void userListByUser() throws Exception {
        // When
        ResultActions resultActions = mvc.perform(get("/api/user/user-list")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "tester", roles = {"USER", "MANAGER"})
    void myAuthorities() throws Exception {
        // When
        ResultActions resultActions = mvc.perform(get("/api/user/authorities")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void userAuthoritiesByAdmin() throws Exception {
        // When
        ResultActions resultActions = mvc.perform(get("/api/user/authorities/" + "manager")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isOk());
    }

    @Test
    void usernameValidation() throws Exception {
        // Given
        String[] usernames = new String[]{"admin", "server", "tester01", "ahngbeom"};

        // When & Then
        for (String username : usernames) {
            MvcResult mvcResult = mvc.perform(get("/api/user/username-validation")
                            .param("username", username)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            log.info(mvcResult.getResponse().getContentAsString());
        }
    }

    @Test
    void nicknameValidation() throws Exception {
        // Given
        String[] usernames = new String[]{"ADMINISTRATOR", "SERVER0101", "TESTER", "_ahngbeom_0704"};

        // When & Then
        for (String username : usernames) {
            MvcResult mvcResult = mvc.perform(get("/api/user/username-validation")
                            .param("username", username)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            log.info(mvcResult.getResponse().getContentAsString());
        }
    }
}