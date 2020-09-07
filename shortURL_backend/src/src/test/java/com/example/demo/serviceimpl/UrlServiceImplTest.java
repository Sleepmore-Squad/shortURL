package com.example.demo.serviceimpl;

import com.example.demo.dao.UrlDao;
import com.example.demo.entity.Url;
import com.example.demo.service.UrlService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UrlServiceImplTest {
    @Autowired
    private UrlService us;

    @MockBean
    private UrlDao ud;

    @BeforeEach
    void setUp(TestInfo testInfo) {
        System.out.println("Start " + testInfo.getDisplayName());
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        System.out.println("Finish " + testInfo.getDisplayName());
    }

    @Test
    void getOriUrl() {
        String shortURL = "abc";
        String oriURL = "http://www.baidu.com";

        when(ud.existsByShortUrl(shortURL)).thenReturn(true).thenReturn(false);
        when(ud.getOriUrl(shortURL)).thenReturn(oriURL);

        assertEquals(oriURL, us.getOriUrl(shortURL));
        assertEquals("101", us.getOriUrl(shortURL));
    }

    @Test
    void getOriUrlById() {
        Integer id = 1;
        String oriURL = "http://www.baidu.com";
        Url url = new Url();
        url.setOriURL(oriURL);
        Optional<Url> urls = Optional.ofNullable(url);

        when(ud.getById(id)).thenReturn(urls).thenReturn(Optional.empty());
        assertEquals(oriURL, us.getOriUrlById(id));
        assertEquals("1#1", us.getOriUrlById(id));
    }

    @Test
    void checkExists() {
        String shortURL = "123";
        when(ud.existsByShortUrl(shortURL)).thenReturn(true);
        assertEquals(true, us.checkExists(shortURL));
    }

    @Test
    void insertUrl() {
        String shortURL = "123";
        String oriURL = "http://www.baidu.com";
        us.insertUrl(shortURL, oriURL);
        verify(ud, times(1)).insertUrl(shortURL, oriURL);
    }

    @Test
    void getCount() {
        when(ud.getCount()).thenReturn(1L);
        assertEquals(1L, us.getCount());
    }
}