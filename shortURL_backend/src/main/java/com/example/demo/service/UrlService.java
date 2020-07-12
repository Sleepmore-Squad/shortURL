package com.example.demo.service;

public interface UrlService {
    String getOriUrl(String shortUrl);

    boolean checkExists(String shortUrl);

    Integer insertUrl(String shortURL, String oriURL);

    Long getCount();
}
