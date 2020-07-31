package com.example.demo.controller;

import com.example.demo.bean.Response;
import org.apache.shiro.ShiroException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExceptionControllerTest {
    @Autowired
    private ExceptionController ec;

    @BeforeEach
    void setUp(TestInfo testInfo) {
        System.out.println("Start " + testInfo.getDisplayName());
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        System.out.println("Finish " + testInfo.getDisplayName());
    }

    @Test
    void handle401() {
        ShiroException e = new ShiroException();
        Response r = ec.handle401(e);
        assertEquals(r.getCode(), 401);
        assertEquals(r.getMsg(), e.getMessage());
        assertNull(r.getData());
        assertNull(r.getId());
    }

    @Test
    void testHandle401() {
        Response r = ec.handle401();
        assertEquals(r.getCode(), 401);
        assertEquals(r.getMsg(), "Unauthorized");
        assertNull(r.getData());
        assertNull(r.getId());
    }

    @Test
    void globalException() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        Throwable ex = new Throwable();
        Response r = ec.globalException(request, ex);

        assertEquals(r.getMsg(),ex.getMessage());
        assertNull(r.getId());
        assertNull(r.getData());

        request.setAttribute("javax.servlet.error.status_code", 404);
        r = ec.globalException(request, ex);
        assertEquals(r.getCode(), 404);
    }
}