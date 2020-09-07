package com.example.demo.dao;


import com.example.demo.entity.Url;

import java.util.Optional;

public interface UrlDao {
    Boolean existsByShortUrl(String shortUrl);

    String getOriUrl(String shortUrl);

    Optional<Url> getById(Integer id);

    Integer insertUrl(String shortUrl, String oriUrl);

    Long getCount();
}
