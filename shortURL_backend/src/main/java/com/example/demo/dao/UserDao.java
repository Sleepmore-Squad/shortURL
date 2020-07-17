package com.example.demo.dao;

import com.example.demo.entity.User;

import java.util.List;

public interface UserDao {
    User findOne(String username, String password);

    List<User> findAll();

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Integer register(User user);

    User findByUsername(String username);

    Integer getId(String username);
}
