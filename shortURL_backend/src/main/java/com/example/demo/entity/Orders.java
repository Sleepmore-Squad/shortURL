package com.example.demo.entity;


import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "url_id")
    private Integer urlId;
    private Date date;

//    public Orders(Integer id, Integer userId, Integer urlId, Date date)
//    {
//        this.id = id;
//        this.userId = userId;
//        this.urlId = urlId;
//        this.date = date;
//    }
}
