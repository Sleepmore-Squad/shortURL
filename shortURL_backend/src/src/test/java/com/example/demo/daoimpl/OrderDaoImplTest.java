package com.example.demo.daoimpl;

import com.example.demo.dao.OrderDao;
import com.example.demo.entity.Orders;
import com.example.demo.repository.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.LinkedList;
import java.util.List;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderDaoImplTest {
    @MockBean
    private OrderRepository or;

    @Autowired
    private OrderDao od;

    @BeforeEach
    void setUp(TestInfo testInfo) {
        System.out.println("Start " + testInfo.getDisplayName());
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        System.out.println("Finish " + testInfo.getDisplayName());
    }

    @Test
    public void findByUser() {
        List<Orders> orders = new LinkedList<>();
        Orders order = new Orders();
        order.setId(1);
        order.setUrlId(1);
        order.setUserId(1);
        order.setDate(null);
        orders.add(order);

        when(or.findByUserId(1)).thenReturn(orders);
        assertEquals(1, od.findByUser(1).size());
    }

    @Test
    public void findAll() {
        List<Orders> orders = new LinkedList<>();
        Orders order = new Orders();
        order.setId(1);
        order.setUrlId(1);
        order.setUserId(1);
        order.setDate(null);
        orders.add(order);

        when(or.findAll()).thenReturn(orders);
        assertEquals(1, od.findAll().size());
    }

    @Test
    public void CountByUserAndDate() {
        Integer user_id = 1;
        long currentTime = System.currentTimeMillis();
        Date date = new Date(currentTime);

        when(or.countByUserIdAndDate(user_id, date)).thenReturn(1);
        assertEquals(1, od.CountByUserAndDate(user_id, date));

    }

    @Test
    public void InsertOrder() {
        od.insertOrder(1,1);
        Date date = new Date(2020);
        Orders toadd = new Orders();
        toadd.setDate(date);
        toadd.setUrlId(1);
        toadd.setUserId(1);
        verify(or, times((1))).saveAndFlush(toadd);
    }
}