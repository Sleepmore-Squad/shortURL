package com.example.demo.daoimpl;

import com.example.demo.dao.UrlDao;
import com.example.demo.entity.Url;
import com.example.demo.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UrlDaoImpl implements UrlDao {
    @Autowired
    private UrlRepository urlRepository;

    @Override
    public Boolean existsByShortUrl(String shortUrl) {
        return urlRepository.existsByShortURL(shortUrl);
    }

    @Override
    public String getOriUrl(String shortUrl) {
        Url url = urlRepository.findByShortURL(shortUrl);
        return url.getOriURL();
    }

    @Override
    public Optional<Url> getById(Integer id) {
        return urlRepository.findById(id);
    }

    @Override
    public Integer insertUrl(String shortUrl, String oriUrl) {
        Url toInsert = new Url();
        toInsert.setShortURL(shortUrl);
        toInsert.setOriURL(oriUrl);
        toInsert.setBlocked(false);
        Url result = urlRepository.saveAndFlush(toInsert);
        return result.getId();
    }

    @Override
    public void blockUrl(Integer id) {
        Url toBlock = urlRepository.getOne(id);
        toBlock.setBlocked(true);
        urlRepository.saveAndFlush(toBlock);
    }

    @Override
    public void addVT(Integer id) {
        Url toAdd = urlRepository.getOne(id);
        Integer vt = toAdd.getVtime();
        toAdd.setVtime(vt + 1);
        urlRepository.saveAndFlush(toAdd);
    }

    @Override
    public Long getCount() {
        return urlRepository.count();
    }
}
