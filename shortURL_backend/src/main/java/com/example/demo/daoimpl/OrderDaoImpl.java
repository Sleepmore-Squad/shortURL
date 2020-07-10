package com.example.demo.daoimpl;

import com.example.demo.dao.OrderDao;
import com.example.demo.entity.Orders;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;


@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Orders> findByUser(Integer user_id) {
        return orderRepository.findByUser_id(user_id);
    }

    @Override
    public List<Orders> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Integer CountByUserAndDate(Integer user_id, Date date) {
        return orderRepository.countByUser_idAndDate(user_id, date);
    }
}
