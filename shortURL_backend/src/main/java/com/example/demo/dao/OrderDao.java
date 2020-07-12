package com.example.demo.dao;

import com.example.demo.entity.Orders;

import java.sql.Date;
import java.util.List;

public interface OrderDao {
    List<Orders> findByUser(Integer user_id);

    List<Orders> findAll();

    Integer CountByUserAndDate(Integer user_id, Date date);

    void insertOrder(Integer user_id, Integer url_id);
}
