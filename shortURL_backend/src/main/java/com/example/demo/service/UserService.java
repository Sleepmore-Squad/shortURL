package com.example.demo.service;

import com.example.demo.entity.User;

import java.util.List;

public interface UserService  {
    Integer login(User user);
    Integer register(User user);
    List<User> showAllUser();
}
