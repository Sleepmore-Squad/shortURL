package com.example.demo.controller;

import com.example.demo.bean.MailCheck;
import com.example.demo.bean.ResultData;
import com.example.demo.entity.Orders;
import com.example.demo.entity.User;
import com.example.demo.service.MailService;
import com.example.demo.service.OrderService;
import com.example.demo.service.UrlService;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
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

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {
    private MockMvc mm;

    @Autowired
    private WebApplicationContext wac;

//    @Autowired
//    private UrlService us;

    private ObjectMapper om = new ObjectMapper();

    @MockBean
    private UserService us;

    @MockBean
    private MailService ms;


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
    void login() throws Exception {
        ResultData rd = new ResultData();
        rd.setData(101);
        String username = "username";
        String password = "password";

        when(us.login(username, password)).thenReturn(rd);

        MvcResult mr = mm.perform(post("/user/login")
                .param("username", username)
                .param("password", password))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(101))
                .andReturn();
    }

    @Test
    void register() throws Exception {
        String username = "username";
        String password = "password";
        String email = "306555437@qq.com";

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setIs_admin(false);

        ResultData rd = new ResultData();
        rd.setData(101);

        when(us.register(user)).thenReturn(rd);

        MvcResult mr = mm.perform(post("/user/register")
                .param("username", username)
                .param("password", password)
                .param("email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(101))
                .andReturn();
    }

    @Test
    void sendMail() throws Exception {
        String email = "306555437@qq.com";

        MvcResult mr = mm.perform(post("/user/register/send")
                .param("email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(201))
                .andReturn();
        verify(ms, times(1)).sendMail(email);
    }

    @Test
    void checkMail() throws Exception {
        String email = "306555437@qq.com";
        String veriCode = "abc";

        MailCheck mailCheck = new MailCheck();
        mailCheck.setEmail(email);
        mailCheck.setVerificationCode(veriCode);

        when(ms.verification(mailCheck)).thenReturn(true);

        MvcResult mr = mm.perform(post("/user/register/check")
                .param("email", email)
                .param("veriCode", veriCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(201))
                .andReturn();
    }

    @Test
    void showAll() throws Exception {
        MvcResult mr = mm.perform(get("/user/showAll"))
                .andExpect(status().isOk())
                .andReturn();

        String resultContent = mr.getResponse().getContentAsString();
        List<User> users = om.readValue(resultContent, new TypeReference<List<User>>() {});

        assertEquals(us.showAllUser().size(), users.size());
    }
}