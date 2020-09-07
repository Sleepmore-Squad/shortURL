package com.example.demo.serviceimpl;

import com.example.demo.bean.MailCheck;
import com.example.demo.service.MailService;
import com.example.demo.serviceimpl.MailServiceImpl;
import com.example.demo.util.MailUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringBootTest
class MailServiceImplTest {
    @Autowired
    private MailService ms;

    @Autowired
    private RedisTemplate redisTemplate;

    @MockBean
    private JavaMailSender javaMailSender;

    @BeforeEach
    void setUp(TestInfo testInfo) {
        System.out.println("Start " + testInfo.getDisplayName());
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        System.out.println("Finish " + testInfo.getDisplayName());
    }

    @Test
    void sendMail() {
        String emailAddress = "306555437@qq.com";
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("Verification Code");
        simpleMailMessage.setFrom("shortURL_official@163.com");
        simpleMailMessage.setTo(emailAddress);

        String verification = MailUtil.generateCode();
        simpleMailMessage.setText("This is your verification code: " + verification);

        ms.sendMail(emailAddress);
        verify(javaMailSender, times(1)).send(simpleMailMessage);
        assertEquals(redisTemplate.getExpire(emailAddress), -1);

    }
    @Test
    void verification() {
        MailCheck mc = new MailCheck();
        mc.setEmail("306555437@qq.com");
        mc.setVerificationCode("abc");

        boolean test = ms.verification(mc);
        assertEquals(true, test);

        mc.setVerificationCode(null);
        test = ms.verification(mc);
        assertEquals(false, test);
    }
}