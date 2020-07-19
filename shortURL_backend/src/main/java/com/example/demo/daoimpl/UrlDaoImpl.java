package com.example.demo.daoimpl;

import com.example.demo.dao.UrlDao;
import com.example.demo.entity.Url;
import com.example.demo.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UrlDaoImpl implements UrlDao {
    @Autowired
    private UrlRepository urlRepository;

    @Override
    public boolean existsByShortUrl(String shortUrl) {
        return urlRepository.existsByShortURL(shortUrl);
    }

    @Override
    public String getOriUrl(String shortUrl) {
        Url url = urlRepository.findByShortURL(shortUrl);
        return url.getOriURL();
    }

    @Override
    public Integer insertUrl(String shortUrl, String oriUrl) {
        Url toInsert = new Url();
        toInsert.setShortURL(shortUrl);
        toInsert.setOriURL(oriUrl);
        Url result = urlRepository.saveAndFlush(toInsert);
        return result.getId();
    }

    @Override
    public Long getCount() {
        return urlRepository.count();
    }
}
