package com.example.demo.serviceimpl;

import com.example.demo.dao.UrlDao;
import com.example.demo.entity.Url;
import com.example.demo.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlServiceImpl implements UrlService {
    @Autowired
    private UrlDao urlDao;

    @Override
    public String getOriUrl(String shortUrl) {
        if (urlDao.existsByShortUrl(shortUrl))
            return urlDao.getOriUrl(shortUrl);
        else
            return "101";
    }

    @Override
    public boolean checkExists(String shortUrl) {
        return urlDao.existsByShortUrl(shortUrl);
    }

    @Override
    public void insertUrl(Url url) {
        urlDao.insertUrl(url.getShortURL(), url.getOriURL());

    }
}
