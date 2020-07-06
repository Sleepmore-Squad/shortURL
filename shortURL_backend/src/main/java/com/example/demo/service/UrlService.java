package com.example.demo.service;

import com.example.demo.entity.Url;

public interface UrlService {
    String getOriUrl(String shortUrl);
    boolean checkExists(String shortUrl);
    void insertUrl(Url url);
}
