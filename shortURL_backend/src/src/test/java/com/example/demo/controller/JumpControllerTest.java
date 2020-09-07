package com.example.demo.controller;

import com.example.demo.service.UrlService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class JumpControllerTest {
    private MockMvc mm;

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private UrlService us;

    @BeforeEach
    void setUp(TestInfo testInfo) {
        System.out.println("Start " + testInfo.getDisplayName());
        mm = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        System.out.println("Finish " + testInfo.getDisplayName());
    }

    @Test
    void jump() throws Exception {
        when(us.getOriUrlById(1)).thenReturn("http://www.baidu.com").thenReturn("1#1");

        mm.perform(get("/to/1"))
                .andExpect(status().is(302))
                .andReturn();
        mm.perform(get("/to/1"))
                .andExpect(status().is(302))
                .andReturn();
    }
}