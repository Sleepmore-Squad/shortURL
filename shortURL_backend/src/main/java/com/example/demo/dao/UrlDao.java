package com.example.demo.dao;


public interface UrlDao {
    Boolean existsByShortUrl(String shortUrl);
    String getOriUrl(String shortUrl);
    void insertUrl(String shortUrl, String oriUrl);
}
