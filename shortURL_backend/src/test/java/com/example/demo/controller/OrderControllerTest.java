package com.example.demo.controller;

import com.example.demo.entity.Orders;
import com.example.demo.entity.User;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import com.example.demo.shiro.JWTToken;
import com.example.demo.util.JWTUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.mgt.SecurityManager;
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

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class OrderControllerTest {

    private MockMvc mm;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private SecurityManager sm;

    @MockBean
    private OrderService os;

    @MockBean
    private UserService us;


    private ObjectMapper om = new ObjectMapper();

    private void login(String username, String password)
    {
        String token = JWTUtil.sign(username, password);
        JWTToken Token = new JWTToken(token);
        Subject subject = SecurityUtils.getSubject();
        subject.login(Token);
    }

    @BeforeEach
    void setUp(TestInfo testInfo) {
        System.out.println("Start " + testInfo.getDisplayName());
        SecurityUtils.setSecurityManager(sm);
        mm = MockMvcBuilders.webAppContextSetup(wac).build();
        String username = "username";
        String password = "password";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("admin");
        when(us.getByUsername(username)).thenReturn(user);
        login(username, password);
    }

    @AfterEach
    public void tearDown(TestInfo testInfo)
    {
        System.out.println("Finish " + testInfo.getDisplayName());
    }

    @Test
    void findAll() throws Exception {
        MvcResult mr = mm.perform(get("/order/findAll"))
                .andExpect(status().isOk())
                .andReturn();
        String resultContent = mr.getResponse().getContentAsString();
        List<Orders> orders = om.readValue(resultContent, new TypeReference<List<Orders>>() {
        });

        assertEquals(os.getAllOrders().size(), orders.size());
    }

    @Test
    void findByUser() throws Exception {
        MvcResult mr = mm.perform(get("/order/findByUser/1"))
                .andExpect(status().isOk())
                .andReturn();
        String resultContent = mr.getResponse().getContentAsString();
        List<Orders> orders = om.readValue(resultContent, new TypeReference<List<Orders>>() {});

        assertEquals(os.getByUser(1).size(), orders.size());
    }

    @Test
    void count() throws Exception {
        Date date = new Date(System.currentTimeMillis());
        System.out.println(date);
//        when(os.countUserDaily(4, date)).thenReturn(3);

        mm.perform(get("/order/count/4/" + date))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(0))
                .andReturn();
    }
}