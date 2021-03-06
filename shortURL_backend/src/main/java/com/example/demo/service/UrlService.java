package com.example.demo.service;

public interface UrlService {
    String getOriUrl(String shortUrl);

    String getOriUrlById(Integer id);

    boolean checkExists(String shortUrl);

    Integer insertUrl(String shortURL, String oriURL);

    void blockUrl(Integer id);

    void addVTime(Integer id);

    Long getCount();
}
