package com.example.springcloud.controller;
import com.example.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    //正常访问，一定OK
    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        String result = paymentService.paymentInfo_OK(id);
        log.info("******result: "+result);
        //******result: 线程池： http-nio-8001-exec-8 paymentInfo_OK,id:  6	ovo
        return result;//线程池： http-nio-8001-exec-8 paymentInfo_OK,id: 6 ovo
    }

    //模拟睡眠3秒钟之后访问
    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id){
        String result = paymentService.paymentInfo_TimeOut(id);
        log.info("******result: "+result);
        //******result: 线程池： http-nio-8001-exec-4 paymentInfo_TimeOut,id:  6	ovo  耗时(秒): 3
        return result;//线程池： http-nio-8001-exec-4 paymentInfo_TimeOut,id: 6 ovo 耗时(秒): 3
    }
}
