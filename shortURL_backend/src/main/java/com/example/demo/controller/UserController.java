package com.example.demo.controller;


import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Integer Login(@RequestBody User user) { return userService.login(user); }

    @PostMapping("/register")
    public Integer Register(@RequestBody User user) { return userService.register(user); }

    @GetMapping("/showAll")
    public List<User> ShowAll() {
        return userService.showAllUser();
    }


}
