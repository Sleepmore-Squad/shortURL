package com.example.demo.controller;


import com.example.demo.entity.Url;
import com.example.demo.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    @GetMapping("/{shortUrl}")
    public void JumpToOri(@PathVariable("shortUrl") String shortUrl, HttpServletResponse response) throws IOException {
        String originUrl = urlService.getOriUrl(shortUrl);
        if(originUrl.equals("101"))
            response.sendRedirect("http://www.baidu.com");
        else
            response.sendRedirect(originUrl);
    }

    @PostMapping("/insert")
    public void Insert(@RequestBody Url url) {
        urlService.insertUrl(url);
    }
}
