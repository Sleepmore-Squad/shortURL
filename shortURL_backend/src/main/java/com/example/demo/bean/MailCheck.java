package com.example.demo.bean;

import lombok.Data;

@Data
public class MailCheck {
    private String email;
    private String verificationCode;
}
