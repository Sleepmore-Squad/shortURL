package com.example.demo.serviceimpl;

import com.example.demo.bean.Response;
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
    public Response register(User user) {
        boolean username_check = userDao.existsByUsername(user.getUsername());
        boolean email_check = userDao.existsByEmail(user.getEmail());
        Response response = new Response();
        if (username_check && email_check)
            response.setCode(111);
        else if (username_check)
            response.setCode(110);
        else if (email_check)
            response.setCode(101);
        else {
            response.setCode(201);
            userDao.register(user);
        }
        return response;

    }

    @Override
    public List<User> showAllUser() {
        return userDao.findAll();
    }

    @Override
    public User getByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public Integer getId(String username) {
        return userDao.getId(username);
    }
}
