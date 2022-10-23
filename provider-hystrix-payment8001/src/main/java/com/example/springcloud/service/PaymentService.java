package com.example.springcloud.service;

public interface PaymentService {

    //正常访问，一定OK
    public String paymentInfo_OK(Integer id);

    //模拟睡眠3秒钟之后访问
    public String paymentInfo_TimeOut(Integer id);

}
