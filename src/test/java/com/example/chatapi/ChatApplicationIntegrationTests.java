package com.example.chatapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.transaction.Transactional;

import java.net.URI;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest // Test를 위한 Application Context 로딩 및 관련 속성 제공
@Disabled // 해당 테스트 클래스 또는 테스트 메소드를 실행하지 않음.
@AutoConfigureMockMvc // MockMvc를 이용한 테스트를 진행하기 위해 필요한 어노테이션
@Transactional // 클래스 내부의 각각의 테스트 메소드가 실행될 때마다, DB 롤백.
@ActiveProfiles("local")
public class ChatApplicationIntegrationTests {

    protected static final Logger log = LoggerFactory.getLogger(ChatApplicationIntegrationTests.class);

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper objectMapper;

}
