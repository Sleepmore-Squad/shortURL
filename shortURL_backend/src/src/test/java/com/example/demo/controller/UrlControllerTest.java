package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.OrderService;
import com.example.demo.service.UrlService;
import com.example.demo.service.UserService;
import com.example.demo.shiro.JWTToken;
import com.example.demo.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UrlControllerTest {
    private MockMvc mm;

    private String username = "username";
    private String password = "password";

    @Autowired
    private WebApplicationContext wac;

    private ObjectMapper om = new ObjectMapper();

    @Autowired
    private SecurityManager sm;

    @MockBean
    private UrlService us;

    @MockBean
    private UserService userService;

    @MockBean
    private OrderService os;
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
        when(userService.getByUsername(username)).thenReturn(user);
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
    void getCount() throws Exception {
        when(us.getCount()).thenReturn(4l);
        mm.perform(get("/url/getCount")
                .header("Authorization", JWTUtil.sign(username, password)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(4))
                .andReturn();
    }

    @Test
    void checkExist() throws Exception {
        when(us.checkExists("1")).thenReturn(true);
        when(us.checkExists("5")).thenReturn(false);

        mm.perform(get("/url/check/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true))
                .andReturn();

        mm.perform(get("/url/check/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false))
                .andReturn();
    }

    @Test
    void getOri() throws Exception {
        when(us.getOriUrl("1")).thenReturn("http://www.baidu.com");

        mm.perform(get("/url/getOri/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("http://www.baidu.com"))
                .andReturn();
    }

    @Test
    void insert() throws Exception {
        String shortURL = "abc";
        String OriURL = "http://www.baidu.com";
        Integer user_id = 1;
        Integer url_id = 1;
        when(us.insertUrl(shortURL, OriURL)).thenReturn(url_id);

        mm.perform(post("/url/insert")
                .param("shortURL", shortURL)
                .param("oriURL", OriURL)
                .param("user_id", String.valueOf(user_id)))
                .andExpect(status().isOk())
                .andReturn();

        verify(us, times(1)).insertUrl(shortURL, OriURL);
        verify(os, times(1)).insertOrder(user_id, url_id);
    }
}