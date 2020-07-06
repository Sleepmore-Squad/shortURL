package com.example.demo.serviceimpl;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public Integer login(User user) {
        User check = userDao.findOne(user.getUsername(),user.getPassword());
        if (check == null)
            return 101;
        if (check.getIs_admin())
            return 401;
        return 201;

    }

    @Override
    public Integer register(User user) {
        boolean username_check = userDao.existsByUsername(user.getUsername());
        boolean email_check = userDao.existsByEmail(user.getEmail());
        if (username_check && email_check)
            return 111;
        if (username_check)
            return 110;
        if (email_check)
            return 101;
        return 201;

    }

    @Override
    public List<User> showAllUser() {
        return userDao.findAll();
    }
}
