package com.example.demo.controller;

import com.example.demo.entity.Orders;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/findAll")
    public List<Orders> FindAll() {
        return orderService.getAllOrders();
    }

    @GetMapping("/findByUser/{id}")
    public List<Orders> FindByUser(@PathVariable("id") Integer id) {
        return orderService.getByUser(id);
    }

    @GetMapping("/count/{id}/{date}")
    public Integer Count(@PathVariable("id") Integer id, @PathVariable("date") Date date) {
        return orderService.countUserDaily(id, date);
    }
}
