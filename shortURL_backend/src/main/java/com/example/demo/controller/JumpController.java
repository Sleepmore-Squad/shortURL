package com.example.demo.controller;

import com.example.demo.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/to")
public class JumpController {
    @Autowired
    private UrlService urlService;

    @GetMapping("/{shortURL}")
    public void Jump(@PathVariable("shortURL") String shortURL, HttpServletResponse response) throws IOException {
        int len = shortURL.length();

        //shortURL反转
        String tmp_str = new StringBuilder(shortURL).reverse().toString();

        //String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char currentBit;//当前位上的字符
        int currentNum;//当前位上的字符对应10进制的数字大小
        int url_id = 0;

        for (int i = 0; i < len; i++) {
            currentBit = tmp_str.charAt(i);
            if (currentBit >= '0' && currentBit <= '9')
                currentNum = currentBit - '0';
            else if (currentBit >= 'a' && currentBit <= 'z')
                currentNum = currentBit - 'a' + 10;
            else if (currentBit >= 'A' && currentBit <= 'Z')
                currentNum = currentBit - 'A' + 36;
            else {
                response.sendRedirect("http://www.bing.com");
                return;
            }
            //currentNum = chars.indexOf(currentBit);//查currentBit是chars里的第几位，太慢了
            url_id += currentNum * Math.pow(62, i);
        }

        String OriURL = urlService.getOriUrlById(url_id);
        //System.out.println(OriURL);
        if (OriURL.equals("1#1"))
            response.sendRedirect("http://www.bing.com");
        else {
            response.sendRedirect(OriURL);
            urlService.addVTime(url_id);
        }
    }
}
