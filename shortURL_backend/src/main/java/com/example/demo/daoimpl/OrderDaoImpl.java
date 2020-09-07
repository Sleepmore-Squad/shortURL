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
        return orderRepository.findByUserId(user_id);
    }

    @Override
    public List<Orders> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Integer CountByUserAndDate(Integer user_id, Date date) {
        return orderRepository.countByUserIdAndDate(user_id, date);
    }

    @Override
    public void insertOrder(Integer user_id, Integer url_id) {
        Date date = new Date(System.currentTimeMillis());
        Orders toadd = new Orders();
        toadd.setDate(date);
        toadd.setUrlId(url_id);
        toadd.setUserId(user_id);
        orderRepository.saveAndFlush(toadd);

    }

    @Override
    public List<Orders> findAllByTime(Date startDate, Date endDate) {
        return orderRepository.findByDateBetween(startDate, endDate);
    }
}
