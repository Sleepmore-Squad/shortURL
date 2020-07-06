package com.example.demo.controller;


import com.example.demo.entity.Url;
import com.example.demo.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/url")
public class UrlController {
    @Autowired
    private UrlService urlService;

    @GetMapping("/check/{shortURL}")
    public boolean CheckExist(@PathVariable("shortURL") String shortURL) {
        return urlService.checkExists(shortURL);
    }

    @GetMapping("/getOri/{shortURL}")
    public String GetOri(@PathVariable("shortURL") String shortURL) {
        return urlService.getOriUrl(shortURL);
    }

    @PostMapping("/insert")
    public void Insert(@RequestBody Url url) {
        urlService.insertUrl(url);
    }
}
