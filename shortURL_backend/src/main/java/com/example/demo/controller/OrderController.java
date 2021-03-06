package com.example.demo.controller;

import com.example.demo.entity.Orders;
import com.example.demo.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private static final Logger LOGGER = LogManager.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @GetMapping("/findAll")
    @RequiresRoles("admin")
    public List<Orders> FindAll() {
        return orderService.getAllOrders();
    }

    @PostMapping("/findByUser")
    @RequiresAuthentication
    public List<Orders> FindByUser(@RequestParam(value = "user_id") Integer id) {
        return orderService.getByUser(id);
    }

    @PostMapping("/getDataByTime")
    @RequiresRoles("admin")
    public List<Orders> FindAllByTime(@RequestParam(value = "begin_time") String start, @RequestParam(value = "end_time") String end) {
        return orderService.findAllByTime(start, end);
    }

    @GetMapping("/count/{id}/{date}")
    @RequiresAuthentication
    public Integer Count(@PathVariable("id") Integer id, @PathVariable("date") Date date) {
        return orderService.countUserDaily(id, date);
    }
}
