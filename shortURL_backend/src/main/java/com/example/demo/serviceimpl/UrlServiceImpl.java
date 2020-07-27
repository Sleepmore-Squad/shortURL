package com.example.demo.serviceimpl;

import com.example.demo.dao.UrlDao;
import com.example.demo.entity.Url;
import com.example.demo.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public String getOriUrlById(Integer id) {
        Optional<Url> url = urlDao.getById(id);
        if (url.isPresent()) {
            return url.get().getOriURL();
        }
        else
            return "1#1";
    }

    @Override
    public boolean checkExists(String shortUrl) {
        return urlDao.existsByShortUrl(shortUrl);
    }

    @Override
    public Integer insertUrl(String shortURL, String oriURL) {
        return urlDao.insertUrl(shortURL, oriURL);
    }

    @Override
    public Long getCount() {
        return urlDao.getCount();
    }
}
