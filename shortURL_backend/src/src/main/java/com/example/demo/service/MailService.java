package com.example.demo.service;

import com.example.demo.bean.MailCheck;

public interface MailService {
    void sendMail(String emailAddress);

    Boolean verification(MailCheck mailCheck);
}
