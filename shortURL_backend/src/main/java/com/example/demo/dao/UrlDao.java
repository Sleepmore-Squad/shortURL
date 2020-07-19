package com.example.demo.dao;


public interface UrlDao {
    boolean existsByShortUrl(String shortUrl);

    String getOriUrl(String shortUrl);

    Integer insertUrl(String shortUrl, String oriUrl);

    Long getCount();
}
