package com.example.demo.controller;

import com.example.demo.bean.MailCheck;
import com.example.demo.bean.Response;
import com.example.demo.entity.User;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.service.MailService;
import com.example.demo.service.UserService;
import com.example.demo.shiro.JWTToken;
import com.example.demo.shiro.MyRealm;
import com.example.demo.util.JWTUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {
    private MockMvc mm;

    @Autowired
    private WebApplicationContext wac;

    private ObjectMapper om = new ObjectMapper();

    @MockBean
    private UserService us;

    @MockBean
    private MailService ms;

    @Autowired
    private SecurityManager sm;
    private Subject subject;

    private MockHttpServletRequest mockHttpServletRequest;
    private MockHttpServletResponse mockHttpServletResponse;

    private void login(String username, String password)
    {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("admin");
        String token = JWTUtil.sign(username, password);
        JWTToken Token = new JWTToken(token);
        subject = new WebSubject.Builder(mockHttpServletRequest, mockHttpServletResponse)
                .buildWebSubject();
        when(us.getByUsername(username)).thenReturn(user);
        subject.login(Token);
        ThreadContext.bind(subject);
    }

    @BeforeEach
    void setUp(TestInfo testInfo) {
        System.out.println("Start " + testInfo.getDisplayName());
        SecurityUtils.setSecurityManager(sm);
        mm = MockMvcBuilders.webAppContextSetup(wac).build();
        mockHttpServletRequest = new MockHttpServletRequest(wac.getServletContext());
        mockHttpServletResponse = new MockHttpServletResponse();
        MockHttpSession mockHttpSession = new MockHttpSession(wac.getServletContext());
        mockHttpServletRequest.setSession(mockHttpSession);
        String username = "username";
        String password = "password";
        login(username, password);
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        System.out.println("Finish" + " " + testInfo.getDisplayName());
    }

    @Test
    void login() throws Exception {

        String username = "username";
        String password = "password";
        String adminName = "adminName";
        String adminPassword = "adminPassword";

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("user");

        User admin = new User();
        admin.setUsername(adminName);
        admin.setPassword(adminPassword);
        admin.setRole("admin");

        User wrong = new User();
        wrong.setUsername(username);
        wrong.setPassword("wrong");

        when(us.getByUsername(username)).thenReturn(user).thenReturn(wrong);
        when(us.getByUsername(adminName)).thenReturn(admin);

        /*
        测试：登录时user的role为"user"时的返回值
         */
        mm.perform(post("/user/login")
                .param("username", username)
                .param("password", password))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(201))
                .andExpect(jsonPath("$.msg").value("Login success"))
                .andExpect(jsonPath("$.data").value(Objects.requireNonNull(JWTUtil.sign(username, password))))
                .andExpect(jsonPath("$.id").value(0))
                .andReturn();
        /*
        测试：登录时user的role为"admin"时的返回值
         */
        mm.perform(post("/user/login")
                .param("username", adminName)
                .param("password", adminPassword))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(401))
                .andExpect(jsonPath("$.msg").value("Login success"))
                .andExpect(jsonPath("$.data").value(Objects.requireNonNull(JWTUtil.sign(adminName, adminPassword))))
                .andExpect(jsonPath("$.id").value(0))
                .andReturn();
        /*
        测试：登录时密码不正确
         */
        mm.perform(post("/user/login")
                .param("username", username)
                .param("password", password))
                .andExpect(status().is(401))
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
        user.setRole("user");
        Response r = new Response();
        r.setCode(201);

        when(us.register(user)).thenReturn(r);
        when(us.getByUsername(username)).thenReturn(user);

        mm.perform(post("/user/register")
                .param("username", username)
                .param("password", password)
                .param("email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(201))
                .andReturn();
    }

    @Test
    void sendMail() throws Exception {
        String email = "306555437@qq.com";
        mm.perform(post("/user/register/send")
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

        when(ms.verification(mailCheck)).thenReturn(true).thenReturn(false);

        mm.perform(post("/user/register/check")
                .param("email", email)
                .param("veriCode", veriCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(201))
                .andReturn();

        mm.perform(post("/user/register/check")
                .param("email", email)
                .param("veriCode", veriCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(101))
                .andReturn();
    }

    @Test
    void showAll() throws Exception {
        String adminName = "username";
        String adminPassword = "password";

        MvcResult mr = mm.perform(get("/user/showAll")
                .header("Authorization", Objects.requireNonNull(JWTUtil.sign(adminName, adminPassword))))
                .andExpect(status().isOk())
                .andReturn();

        String resultContent = mr.getResponse().getContentAsString();
        List<User> users = om.readValue(resultContent, new TypeReference<List<User>>() {});

        assertEquals(us.showAllUser().size(), users.size());
    }
}