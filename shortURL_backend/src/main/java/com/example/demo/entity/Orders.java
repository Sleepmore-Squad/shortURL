package com.example.demo.entity;


import lombok.Data;
import org.bouncycastle.asn1.eac.UnsignedInteger;

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


    @OneToOne
    @JoinColumn(name = "url_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Url urlinfo;
}
