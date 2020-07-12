package com.example.demo.serviceimpl;

import com.example.demo.bean.MailCheck;
import com.example.demo.service.MailService;
import com.example.demo.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    JavaMailSender javaMailSender;


    @Override
    @Async
    public void sendMail(String emailAddress) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("Verification Code");
        simpleMailMessage.setFrom("shortURL_official@163.com");
        simpleMailMessage.setTo(emailAddress);
        //获取验证码
        String verification = MailUtil.generateCode();
        //将验证码存放进邮箱
        simpleMailMessage.setText("This is your verification code: " + verification);
        //获取redis操作类
        ValueOperations valueOperations = redisTemplate.opsForValue();
        javaMailSender.send(simpleMailMessage);
        /*设置缓存*/
        valueOperations.set(emailAddress, verification);
        /**
         * K key, final long timeout, final TimeUnit unit
         * key 存储数据的key值
         * TimeUnit 时间单位
         * timeout 数据的过期时间
         * */
        redisTemplate.expire(emailAddress, 60 * 5, TimeUnit.SECONDS);

    }

    @Override
    public Boolean verification(MailCheck mailCheck) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        /*从redis中获取验证码*/
        String verificationCode = (String) valueOperations.get(mailCheck.getEmail());
        System.out.println(verificationCode);
        /*判断提交的信息是否正确*/
        if (verificationCode != null && verificationCode.equals(mailCheck.getVerificationCode())) {
            /*删除缓存中的数据*/
            redisTemplate.delete(mailCheck.getEmail());
            return true;
        }
        return false;
    }
}
