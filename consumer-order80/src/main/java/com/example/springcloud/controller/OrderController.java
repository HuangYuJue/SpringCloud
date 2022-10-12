package com.example.springcloud.controller;

import com.example.springcloud.entities.CommonResult;
import com.example.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderController {

    //定义需要调用的地址8001
    public static final String PAYMENT_URL = "http://localhost:8001";

    @Resource//注入
    private RestTemplate restTemplate;

    //因为是客户端，所以一般发的都是get请求
    @GetMapping(value = "/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        //调用restTemplate中的post请求
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
    }

    //因为是客户端，所以一般发的都是get请求
    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        //调用restTemplate中的get请求
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }
}
