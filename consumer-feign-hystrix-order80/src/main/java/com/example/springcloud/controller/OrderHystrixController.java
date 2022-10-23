package com.example.springcloud.controller;

import com.example.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
// 定义全局fallback方法(全局的兜底方法)
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderHystrixController {
    @Resource
    private PaymentHystrixService paymentHystrixService;

    //正常访问，一定OK
    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        String result = paymentHystrixService.paymentInfo_OK(id);
        return result;
    }

    //消费者端服务降级(注意：此时支付端没有服务降级，在规定时间内访问成功)
    /*
    服务降级provider端fallback：
        @HystrixCommand注解是方法级别的，在需要捕获的方法上加上注解：
        fallbackMethod：标记的是捕获异常时需要执行的方法，方法名称跟value值要一样，
        表示如果paymentInfo_TimeOut方法出事了，由paymentInfo_TimeOutHandler方法给他兜底。
        commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")}
        表示timeoutInMilliseconds线程的超时时间是1500(1.5秒钟)，超过1.5秒钟则服务降级，调用备选兜底方法。
     */
//    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")
//    })
    @HystrixCommand //不指明fallback方法(兜底方法)，则用全局fallback方法(全局兜底方法)
    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id){
        String result = paymentHystrixService.paymentInfo_TimeOut(id);
        return result;
    }
    public String paymentInfo_TimeOutHandler(Integer id){
        return "我是消费者80，对方支付系统繁忙请在10秒钟后再试或者自己运行出错检查自己，~^~";
    }

    // 下面是全局fallback方法(全局的兜底方法)
    public String payment_Global_FallbackMethod(){
        return "Global异常处理信息，请稍后再试 *_*";
    }
}
