package com.example.springcloud.service;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT",fallback = PaymentFallbackService.class)：
name/value：指定FeignClient的名称，如果项目使用了Ribbon，name属性会作为微服务的名称，用于服务发现；
fallback: 定义容错的处理类，当调用远程接口失败或超时时，会调用对应接口的容错逻辑，fallback指定的类必须实现@FeignClient标记的接口，
即指定的类必须继承被@FeignClient标记的接口，作为兜底方法fallback方法。
访问http://localhost/consumer/payment/hystrix/ok/8时正常显示，
但是关闭PaymentHystrixMain8001项目之后再访问显示PaymentFallbackService.class中的------PaymentFallbackService fall back-paymentInfo_OK ,~_~
关闭服务端provider后，因为做了服务降级处理，让客户端在服务端不可用时，也会获得提示信息而不会挂起耗死服务器。
 */
@Component
@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT",fallback = PaymentFallbackService.class)
public interface PaymentHystrixService {

    //正常访问，一定OK
    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id);

    //模拟睡眠3秒钟之后访问
    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id);

}
