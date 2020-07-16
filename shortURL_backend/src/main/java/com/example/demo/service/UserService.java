package com.example.demo.service;

import com.example.demo.bean.ResultData;
import com.example.demo.entity.User;

import java.util.List;

public interface UserService {
    ResultData login(String username, String password);

    ResultData register(User user);

    List<User> showAllUser();
}