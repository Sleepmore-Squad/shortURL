package com.example.demo.controller;


import com.example.demo.service.OrderService;
import com.example.demo.service.UrlService;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/url")
public class UrlController {
    private static String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

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

    //短链接跳转到原链接
    @GetMapping("/{shortURL}")
    public void Jump(@PathVariable("shortURL") String shortURL, HttpServletResponse response) throws IOException {
        //下面注释的是将短链接shortURL转化成id再查找原链接OriURL的方法，
        // 但是getOriURL这个函数只能用shortURL作参数，所以暂时先直接返回OriURL
//        int len = shortURL.length();
//
//        //shortURL反转
//        String tmp_str = new StringBuilder(shortURL).reverse().toString();
//
//        char currentBit = 'a';//当前位上的字符
//        int currentNum = 0;//当前位上的字符对应10进制的数字大小
//        int url_id = 0;
//
//        for(int i = 0; i < len; i++)
//        {
//            currentBit = tmp_str.charAt(i);
//            currentNum = chars.indexOf(currentBit);
//            url_id += currentNum * Math.pow(62, i);
//        }
//
//
//        System.out.println(id);
//
//        String OriURL = urlService.getOriUrl();
//        System.out.println(OriURL);
        String OriURL = urlService.getOriUrl(shortURL);
        if(OriURL.equals("101"))
            response.sendRedirect("http://www.baidu.com");
        else
            response.sendRedirect(OriURL);
    }
}
