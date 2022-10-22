package com.example.springcloud.service;

import com.example.springcloud.entities.CommonResult;
import com.example.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component  //扫描接口
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")    //(使用)说明是Feign功能的一个接口，并在里面设置需要找的微服务provider的名称
/**@EnableFeignClients：开启；
 * @FeignClient(value = "CLOUD-PAYMENT-SERVICE")：使用；
 * 且表示在名称为 CLOUD-PAYMENT-SERVICE 的微服务下面调用下面 @GetMapping() 等访问方式中所写的地址，
 * 注意下面 @GetMapping() 等访问方式中所写的地址是在 PaymentController 类(provider)中的访问地址，
 * 而不是 OrderController 类(consumer)中的访问地址，因为要通过CLOUD-PAYMENT-SERVICE访问到provider中的方法。
 * 该行为取消了在 consumer 的 Controller 中的 restTemplate 的使用。
 */
//业务逻辑接口+@FeignClient配置调用provider服务
public interface PaymentFeignService {

    /*
        以下是provider微服务中Controller的方法，注意不是consumer中的方法
     */

    //根据id获取信息
    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);

    //服务提供方provider故意写暂停程序(即耗时将超过3秒钟)
    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeOut();
}
