package com.example.demo.serviceimpl;

import com.example.demo.bean.Response;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserService us;

    @MockBean
    private UserDao ud;

    @BeforeEach
    void setUp(TestInfo testInfo) {
        System.out.println("Start " + testInfo.getDisplayName());
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        System.out.println("Finish " + testInfo.getDisplayName());
    }

    @Test
    void register() {
        String username = "username";
        String email = "email";
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        Response response = new Response();

        when(ud.existsByEmail(email)).thenReturn(true);
        when(ud.existsByUsername(username)).thenReturn(true);
        response.setCode(111);
        assertEquals(response, us.register(user));

        when(ud.existsByEmail(email)).thenReturn(false);
        when(ud.existsByUsername(username)).thenReturn(true);
        response.setCode(110);
        assertEquals(response, us.register(user));

        when(ud.existsByEmail(email)).thenReturn(true);
        when(ud.existsByUsername(username)).thenReturn(false);
        response.setCode(101);
        assertEquals(response, us.register(user));

        when(ud.existsByEmail(email)).thenReturn(false);
        when(ud.existsByUsername(username)).thenReturn(false);
        response.setCode(201);
        assertEquals(response, us.register(user));
        verify(ud, times(1)).register(user);
    }

    @Test
    void showAllUser() {
        User user = new User();
        List<User> users = new LinkedList<>();
        users.add(user);

        when(ud.findAll()).thenReturn(users);
        assertEquals(1,us.showAllUser().size());
    }

    @Test
    void getByUsername() {
        String username = "username";
        User user = new User();
        user.setUsername(username);

        when(ud.findByUsername(username)).thenReturn(user);
        assertEquals(user, us.getByUsername(username));
    }

    @Test
    void getId() {
        String username = "username";
        Integer id = 1;
        User user = new User();
        user.setUsername(username);
        user.setId(id);

        when(ud.getId(username)).thenReturn(id);
        assertEquals(id, us.getId(username));
    }
}