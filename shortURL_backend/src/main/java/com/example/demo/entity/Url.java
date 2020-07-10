package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String shortURL;
    private String oriURL;

    public String getShortURL() {return shortURL;}

    public String getOriURL() { return oriURL;}

    public void setShortURL(String shortUrl) { this.shortURL = shortUrl;}

    public void setOriURL(String oriUrl) { this.oriURL = oriUrl;}
}
