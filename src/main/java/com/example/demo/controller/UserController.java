package com.example.demo.controller;


import com.example.demo.bean.MailCheck;
import com.example.demo.bean.ResultData;
import com.example.demo.entity.User;
import com.example.demo.service.MailService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @PostMapping("/login")
    public ResultData Login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        return userService.login(username, password);
    }

    @PostMapping("/register")
    public ResultData Register(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password, @RequestParam(value = "email") String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setIs_admin(false);
        return userService.register(user);
    }

    @PostMapping("/register/send")
    public Integer SendMail(@RequestParam(value = "email") String email) {
        mailService.sendMail(email);
        return 201;
    }

    @PostMapping("/register/check")
    public Integer CheckMail(@RequestParam(value = "email") String email, @RequestParam(value = "veriCode") String veriCode) {
        MailCheck mailCheck = new MailCheck();
        mailCheck.setEmail(email);
        mailCheck.setVerificationCode(veriCode);
        Boolean veri = mailService.verification(mailCheck);
        if (veri)
            return 201;
        return 101;
    }


    @GetMapping("/showAll")
    public List<User> ShowAll() {
        return userService.showAllUser();
    }


}
