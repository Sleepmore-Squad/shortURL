package com.example.demo.controller;


import com.example.demo.service.OrderService;
import com.example.demo.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/url")
public class UrlController {
    @Autowired
    private UrlService urlService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/getCount")
    public Long GetCount() {
        return urlService.getCount();
    }

    @GetMapping("/check/{shortURL}")
    public boolean CheckExist(@PathVariable("shortURL") String shortURL) {
        return urlService.checkExists(shortURL);
    }

    @GetMapping("/getOri/{shortURL}")
    public String GetOri(@PathVariable("shortURL") String shortURL) {
        return urlService.getOriUrl(shortURL);
    }

    @PostMapping("/insert")
    public void Insert(@RequestParam(value = "shortURL") String shortURL, @RequestParam(value = "oriURL") String oriURL, @RequestParam(value = "user_id") Integer user_id) {
        Integer url_id = urlService.insertUrl(shortURL, oriURL);
        orderService.insertOrder(user_id, url_id);
    }
}
