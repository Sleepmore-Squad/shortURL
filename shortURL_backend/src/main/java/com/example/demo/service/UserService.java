package com.example.demo.service;

import com.example.demo.bean.Response;
import com.example.demo.entity.User;

import java.util.List;

public interface UserService {

    Response register(User user);

    List<User> showAllUser();

    User getByUsername(String username);

    Integer getId(String username);
}
