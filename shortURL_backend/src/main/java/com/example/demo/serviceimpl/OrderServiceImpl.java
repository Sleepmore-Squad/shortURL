package com.example.demo.serviceimpl;

import com.example.demo.dao.OrderDao;
import com.example.demo.entity.Orders;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;


    @Override
    public List<Orders> getAllOrders() {
        return orderDao.findAll();
    }

    @Override
    public List<Orders> getByUser(Integer user_id) {
        return orderDao.findByUser(user_id);
    }

    @Override
    public Integer countUserDaily(Integer user_id, Date date) {
        return orderDao.CountByUserAndDate(user_id, date);
    }

    @Override
    public void insertOrder(Integer user_id, Integer url_id) {
        orderDao.insertOrder(user_id, url_id);
    }
}
