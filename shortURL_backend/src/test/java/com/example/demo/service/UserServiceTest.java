package com.example.demo.service;

import com.example.demo.DemoApplication;
import com.example.demo.bean.ResultData;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService us;

    @MockBean
    private UserDao ud;

    @BeforeEach
    public void initEach(TestInfo testInfo)
    {
        System.out.println("Start " + testInfo.getDisplayName());
    }

    @AfterEach
    public void tearDown(TestInfo testInfo)
    {
        System.out.println("Finish " + testInfo.getDisplayName());
    }

    @Test
    public void login() {
        Integer id = 1;
        String username = "username";
        String password = "password";
        ResultData rd = new ResultData();

        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail("306555437@qq.com");
        user.setIs_admin(true);

        when(ud.findOne(username, password)).thenReturn(null).thenReturn(user);

        rd.setCode(101);
        assertEquals(rd, us.login(username, password));

        rd.setCode(401);
        rd.setData(user.getId());
        assertEquals(rd, us.login(username, password));

        user.setIs_admin(false);
        rd.setCode(201);
        rd.setData(user.getId());
        assertEquals(rd, us.login(username, password));
    }

    @Test
    public void register() {
        Integer id = 1;
        String username = "username";
        String password = "password";
        String email = "306555437@qq.com";
        ResultData rd = new ResultData();

        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setIs_admin(true);

        when(ud.existsByEmail(email)).thenReturn(true).thenReturn(false);
        when(ud.existsByUsername(username)).thenReturn(true).thenReturn(false);
        when(ud.register(user)).thenReturn(user.getId());

        rd.setCode(111);
        assertEquals(rd, us.register(user));

        rd.setCode(201);
        rd.setData(user.getId());
        assertEquals(rd, us.register(user));
    }

    @Test
    public void showAllUser() {
        List<User> users = new LinkedList<>();

        User user = new User();
        Integer id = 1;
        String username = "username";
        String password = "password";
        String email = "306555437@qq.com";
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setIs_admin(true);

        users.add(user);
        users.add(user);

        when(ud.findAll()).thenReturn(users);

        assertEquals(2, us.showAllUser().size());
    }
}