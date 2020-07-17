package com.example.demo.controller;

import com.example.demo.bean.MailCheck;
import com.example.demo.bean.Response;
import com.example.demo.bean.ResultData;
import com.example.demo.entity.User;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.service.MailService;
import com.example.demo.service.UserService;
import com.example.demo.util.JWTUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @PostMapping("/login")
    public Response Login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        User user = userService.getByUsername(username);
        if (user.getPassword().equals(password)) {
            if (user.getRole().equals("user"))
                return new Response(201, "Login success", JWTUtil.sign(username, password), userService.getId(username));
            else
                return new Response(401, "Login success", JWTUtil.sign(username, password), userService.getId(username));
        } else {
            throw new UnauthorizedException();
        }
    }

    @PostMapping("/register")
    public ResultData Register(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password, @RequestParam(value = "email") String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole("user");
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
    @RequiresRoles("admin")
    public List<User> ShowAll() {
        return userService.showAllUser();
    }


}
