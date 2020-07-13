package com.example.demo.service;

import com.example.demo.entity.Orders;

import java.sql.Date;
import java.util.List;

public interface OrderService {
    List<Orders> getAllOrders();

    List<Orders> getByUser(Integer user_id);

    Integer countUserDaily(Integer user_id, Date date);

    void insertOrder(Integer user_id, Integer url_id);
}
