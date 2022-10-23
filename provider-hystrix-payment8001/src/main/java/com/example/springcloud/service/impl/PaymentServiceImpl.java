package com.example.springcloud.service.impl;
import com.example.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl implements PaymentService {

    //正常访问，一定OK
    public String paymentInfo_OK(Integer id){
        return "线程池： "+Thread.currentThread().getName()+" paymentInfo_OK,id:  "+id+"\t"+"ovo";
    }

    //模拟睡眠3秒钟之后访问
    public String paymentInfo_TimeOut(Integer id){
        //暂停3秒钟线程
        int timeNumber = 3;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池： "+Thread.currentThread().getName()+" paymentInfo_TimeOut,id:  "+id+"\t"+"ovo"+"  耗时(秒): "+timeNumber;
    }

}
