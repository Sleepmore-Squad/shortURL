package com.example.demo.service;

import com.example.demo.DemoApplication;
import com.example.demo.dao.OrderDao;
import com.example.demo.entity.Orders;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
public class OrderServiceTest {
    @Autowired
    private OrderService os;

    @MockBean
    private OrderDao od;

    @BeforeEach
    public void initEach(TestInfo testInfo)
    {
        System.out.println("Start " + testInfo.getDisplayName());
    }

    @AfterEach
    public void tearDown(TestInfo testInfo)
    {
        System.out.println("Finish " + testInfo.getDisplayName());
    }

    @Test
    public void getAllOrders() {
        List<Orders> orders = new LinkedList<>();
        Orders order = new Orders();
        order.setId(1);
        order.setUrlId(1);
        order.setUserId(1);
        order.setDate(null);
        orders.add(order);
        when(od.findAll()).thenReturn(orders);

        assertEquals(1, os.getAllOrders().size());
    }

    @Test
    public void getByUser() {
        Orders order = new Orders();
        order.setUserId(1);
        order.setUrlId(1);
        order.setId(1);
        order.setDate(null);

        List<Orders> orders = new LinkedList<>();
        orders.add(order);

        when(od.findByUser(1)).thenReturn(orders);
        assertEquals(orders, os.getByUser(1));
    }

    @Test
    public void countUserDaily() {
        Date date = new Date(System.currentTimeMillis());
        when(od.CountByUserAndDate(1,date)).thenReturn(3);
        assertEquals(3, os.countUserDaily(1,date));
    }

    @Test
    public void insertOrder() {
        os.insertOrder(1,1);
        verify(od,times(1)).insertOrder(1,1);
    }
}