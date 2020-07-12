package com.example.demo.serviceimpl;

import com.example.demo.bean.ResultData;
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
    public ResultData login(String username, String password) {
        User check = userDao.findOne(username, password);
        ResultData resultData = new ResultData();
        if (check == null)
            resultData.setCode(101);
        else if (check.getIs_admin()) {
            resultData.setCode(401);
            resultData.setData(check.getId());
        } else {
            resultData.setCode(201);
            resultData.setData(check.getId());
        }
        return resultData;

    }

    @Override
    public ResultData register(User user) {
        boolean username_check = userDao.existsByUsername(user.getUsername());
        boolean email_check = userDao.existsByEmail(user.getEmail());
        ResultData resultData = new ResultData();
        if (username_check && email_check)
            resultData.setCode(111);
        else if (username_check)
            resultData.setCode(110);
        else if (email_check)
            resultData.setCode(101);
        else {
            resultData.setCode(201);
            resultData.setData(userDao.register(user));
        }
        return resultData;

    }

    @Override
    public List<User> showAllUser() {
        return userDao.findAll();
    }
}
