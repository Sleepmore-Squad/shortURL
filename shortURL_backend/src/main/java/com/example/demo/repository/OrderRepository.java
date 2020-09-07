package com.example.demo.repository;

import com.example.demo.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findByUserId(Integer user_id);

    List<Orders> findByDateBetween(Date start, Date end);

    Integer countByUserIdAndDate(Integer user_id, Date date);

}
