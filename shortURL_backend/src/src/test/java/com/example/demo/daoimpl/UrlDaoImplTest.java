package com.example.demo.daoimpl;

import com.example.demo.dao.UrlDao;
import com.example.demo.entity.Url;
import com.example.demo.repository.UrlRepository;
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
class UrlDaoImplTest {
    @MockBean
    private UrlRepository ur;

    @Autowired
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
    void existsByShortUrl() {
        String shortUrl = "123";
        when(ur.existsByShortURL(shortUrl)).thenReturn(true);
        assertEquals(true, ud.existsByShortUrl(shortUrl));
    }

    @Test
    void getOriUrl() {
        String shortUrl = "123";
        String OriUrl = "abc";
        Url url = new Url();
        url.setOriURL(OriUrl);
        url.setShortURL(shortUrl);

        when(ur.findByShortURL(shortUrl)).thenReturn(url);
        assertEquals(OriUrl, ud.getOriUrl(shortUrl));

    }

    @Test
    void getById() {
        Integer url_id = 1;
        Url one_url = new Url();
        one_url.setId(url_id);
        Optional<Url> url = Optional.of(one_url);

        when(ur.findById(url_id)).thenReturn(url);
        assertEquals(url, ud.getById(url_id));
    }

    @Test
    void insertUrl() {
        String shortUrl = "123";
        String OriUrl = "abc";
//        Integer url_id = 1;
        Url url = new Url();
//        url.setId(url_id);
        url.setOriURL(OriUrl);
        url.setShortURL(shortUrl);

        when(ur.saveAndFlush(url)).thenReturn(url);
        assertEquals(url.getId(), ud.insertUrl(shortUrl, OriUrl));
    }

    @Test
    void getCount() {
        Long count = 1L;
        when(ur.count()).thenReturn(count);
        assertEquals(count, ud.getCount());
    }
}