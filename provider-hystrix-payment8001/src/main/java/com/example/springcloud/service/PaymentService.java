package com.example.springcloud.service;

import org.springframework.web.bind.annotation.PathVariable;

public interface PaymentService {

    //正常访问，一定OK
    public String paymentInfo_OK(Integer id);

    //模拟睡眠3秒钟之后访问
    public String paymentInfo_TimeOut(Integer id);

    public String paymentCircuitBreaker(@PathVariable("id") Integer id);

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id);

}
