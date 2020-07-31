package com.example.demo.bean;

import lombok.Data;

@Data
public class Response {
    private Integer code;
    private String msg;
    private Object data;
    private Integer id;

    public Response(){}


    public Response(Integer code, String msg, Object data, Integer id) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.id = id;
    }
}
