package com.example.demo.controller;


import com.example.demo.service.OrderService;
import com.example.demo.service.UrlService;
import org.apache.catalina.filters.ExpiresFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/url")
public class UrlController {
    private static final Logger LOGGER = LogManager.getLogger(UrlController.class);

    @Autowired
    private UrlService urlService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/getCount")
    @RequiresAuthentication
    public Long GetCount() {
        return urlService.getCount();
    }

    @GetMapping("/check/{shortURL}")
    @RequiresAuthentication
    public boolean CheckExist(@PathVariable("shortURL") String shortURL) {
        return urlService.checkExists(shortURL);
    }

    @GetMapping("/getOri/{shortURL}")
    @RequiresAuthentication
    public String GetOri(@PathVariable("shortURL") String shortURL) {
        return urlService.getOriUrl(shortURL);
    }

    @PostMapping("/insert")
    @RequiresAuthentication
    public void Insert(@RequestParam(value = "shortURL") String shortURL, @RequestParam(value = "oriURL") String oriURL, @RequestParam(value = "user_id") Integer user_id) {
        Integer url_id = urlService.insertUrl(shortURL, oriURL);
        orderService.insertOrder(user_id, url_id);
    }

    @PostMapping("/block")
    @RequiresRoles("admin")
    public void Block(@RequestParam(value = "url_id") Integer id) {
        urlService.blockUrl(id);
    }


}
