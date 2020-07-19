package com.example.demo.controller;

import com.example.demo.DemoApplication;
import com.example.demo.service.OrderService;
import com.example.demo.service.UrlService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UrlControllerTest{
    private MockMvc mm;

    @Autowired
    private WebApplicationContext wac;

//    @Autowired
//    private UrlService us;

    private ObjectMapper om = new ObjectMapper();

    @MockBean
    private UrlService us;

    @MockBean
    private OrderService os;


    @BeforeEach
    public void initEach(TestInfo testInfo)
    {
        System.out.println("Start " + testInfo.getDisplayName());
        mm = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @AfterEach
    public void tearDown(TestInfo testInfo)
    {
        System.out.println("Finish " + testInfo.getDisplayName());
    }

    @Test
    public void getCount() throws Exception{
        when(us.getCount()).thenReturn(4l);

        MvcResult mr = mm.perform(get("/url/getCount"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(4))
                .andReturn();
    }

    @Test
    public void checkExist() throws Exception{
        when(us.checkExists("1")).thenReturn(true);
        when(us.checkExists("5")).thenReturn(false);

        MvcResult mr = mm.perform(get("/url/check/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true))
                .andReturn();

        mr = mm.perform(get("/url/check/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false))
                .andReturn();
    }

    @Test
    public void getOri() throws Exception{
        when(us.getOriUrl("1")).thenReturn("http://www.baidu.com");

        MvcResult mr = mm.perform(get("/url/getOri/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("http://www.baidu.com"))
                .andReturn();
    }

    @Test
    public void insert() throws Exception{
        String shortURL = "abc";
        String OriURL = "http://www.baidu.com";
        Integer user_id = 1;
        Integer url_id = 1;
        when(us.insertUrl(shortURL, OriURL)).thenReturn(url_id);

        MvcResult mr = mm.perform(post("/url/insert")
                .param("shortURL", shortURL)
                .param("oriURL", OriURL)
                .param("user_id", String.valueOf(user_id)))
                .andExpect(status().isOk())
                .andReturn();

        verify(us, times(1)).insertUrl(shortURL, OriURL);
        verify(os, times(1)).insertOrder(user_id, url_id);
    }

    @Test
    public void jump() throws Exception{
        when(us.getOriUrl("1")).thenReturn("101");
        MvcResult mr = mm.perform(get("/url/1"))
                .andReturn();
        verify(us, times(1)).getOriUrl("1");
    }
}