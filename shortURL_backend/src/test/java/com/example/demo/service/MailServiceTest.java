package com.example.demo.service;

import com.example.demo.DemoApplication;
import com.example.demo.bean.MailCheck;
import com.example.demo.util.MailUtil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class MailServiceTest {
    @Autowired
    private MailService ms;

//    @MockBean
//    private RedisTemplate redisTemplate;

    @MockBean
    private JavaMailSender javaMailSender;


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
    public void sendMail() {
        String email = "306555437@qq.com";
        String code = MailUtil.generateCode();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("Verification Code");
        simpleMailMessage.setFrom("shortURL_official@163.com");
        simpleMailMessage.setTo(email);

        simpleMailMessage.setText("This is your verification code: " + code);



        ms.sendMail(email);
//        verify(redisTemplate, times(1)).opsForValue();
//        verify(redisTemplate, times(1)).expire(email, 60 * 5, TimeUnit.SECONDS);
        verify(javaMailSender, times(1)).send(simpleMailMessage);

    }

    @Test
    void verification() {
        MailCheck mc = new MailCheck();
        mc.setEmail("306555437@qq.com");
        mc.setVerificationCode("abc");

        boolean test = ms.verification(mc);
        assertEquals(true, test);
    }
}