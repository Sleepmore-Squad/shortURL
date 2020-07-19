package com.example.demo.service;

import com.example.demo.DemoApplication;
import com.example.demo.dao.UrlDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UrlServiceTest {
    @Autowired
    private UrlService us;

    @MockBean
    private UrlDao ud;

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
    public void getOriUrl() {
        String shortURL = "abc";
        String oriURL = "http://www.baidu.com";

        when(ud.existsByShortUrl(shortURL)).thenReturn(true).thenReturn(false);
        when(ud.getOriUrl(shortURL)).thenReturn(oriURL);

        assertEquals(oriURL, us.getOriUrl(shortURL));
        assertEquals("101", us.getOriUrl(shortURL));
    }

    @Test
    public void checkExists() {
        String shortURL = "abc";
        when(ud.existsByShortUrl(shortURL)).thenReturn(true);
        assertEquals(true, us.checkExists(shortURL));
    }

    @Test
    public void insertUrl() {
        String shortURL = "abc";
        String oriURL = "http://www.baidu.com";

        when(ud.insertUrl(shortURL, oriURL)).thenReturn(1);
        assertEquals(1, us.insertUrl(shortURL, oriURL));
    }

    @Test
    public void getCount() {
        when(ud.getCount()).thenReturn(1L);
        assertEquals(1L, us.getCount());
    }
}