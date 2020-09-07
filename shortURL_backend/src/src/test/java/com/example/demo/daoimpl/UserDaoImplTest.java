package com.example.demo.daoimpl;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
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
class UserDaoImplTest {
    @MockBean
    private UserRepository ur;

    @Autowired
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
    void findOne() {
        String name = "name";
        String password = "password";
        User user = new User();
        user.setUsername(name);
        user.setPassword(password);

        when(ur.findByUsernameAndPassword(name, password)).thenReturn(user);
        assertEquals(user, ud.findOne(name, password));
    }

    @Test
    void findAll() {
        String name = "name";
        String password = "password";
        User user = new User();
        user.setUsername(name);
        user.setPassword(password);
        List<User> users = new LinkedList<>();
        users.add(user);

        when(ur.findAll()).thenReturn(users);
        assertEquals(users.size(), ud.findAll().size());
    }

    @Test
    void existsByUsername() {
        String name = "name";

        when(ur.existsByUsername(name)).thenReturn(true);
        assertEquals(true, ud.existsByUsername(name));
    }

    @Test
    void existsByEmail() {
        String email = "email";
        when(ur.existsByEmail(email)).thenReturn(true);
        assertEquals(true, ud.existsByEmail(email));
    }

    @Test
    void register() {
        String name = "name";
        String password = "password";
        User user = new User();
        user.setUsername(name);
        user.setPassword(password);

        when(ur.saveAndFlush(user)).thenReturn(user);
        assertEquals(user.getId(), ud.register(user));
    }

    @Test
    void findByUsername() {
        String name = "name";
        String password = "password";
        User user = new User();
        user.setUsername(name);
        user.setPassword(password);

        when(ur.findByUsername(name)).thenReturn(user);
        assertEquals(user, ud.findByUsername(name));
    }

    @Test
    void getId() {
        String name = "name";
        String password = "password";
        Integer id = 1;
        User user = new User();
        user.setId(id);
        user.setUsername(name);
        user.setPassword(password);

        when(ur.findByUsername(name)).thenReturn(user);
        assertEquals(id, ud.getId(name));
    }
}