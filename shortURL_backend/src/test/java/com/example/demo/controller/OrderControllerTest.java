package com.example.demo.controller;

import com.example.demo.DemoApplication;
import com.example.demo.entity.Orders;
import com.example.demo.service.OrderService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class OrderControllerTest{
    private MockMvc mm;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private OrderService os;

    private ObjectMapper om = new ObjectMapper();

//    @BeforeAll
//    static void init()
//    {
//        mm = MockMvcBuilders.webAppContextSetup(wac).build();
//    }

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
    public void findAll() throws Exception{
        MvcResult mr = mm.perform(get("/order/findAll"))
                .andExpect(status().isOk())
                .andReturn();
        String resultContent = mr.getResponse().getContentAsString();
        List<Orders> orders = om.readValue(resultContent, new TypeReference<List<Orders>>() {});

        assertEquals(os.getAllOrders().size(), orders.size());
    }

    @Test
    public void findByUser() throws Exception {
        MvcResult mr = mm.perform(get("/order/findByUser/1"))
                .andExpect(status().isOk())
                .andReturn();
        String resultContent = mr.getResponse().getContentAsString();
        List<Orders> orders = om.readValue(resultContent, new TypeReference<List<Orders>>() {});

        assertEquals(os.getByUser(1).size(), orders.size());

    }

    @Test
    public void count() throws Exception{
        MvcResult mr = mm.perform(get("/order/count/4/2020-7-16"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(3))
                .andReturn();
    }
}