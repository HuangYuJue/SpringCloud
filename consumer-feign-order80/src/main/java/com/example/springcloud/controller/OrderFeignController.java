package com.example.springcloud.controller;
import com.example.springcloud.entities.CommonResult;
import com.example.springcloud.entities.Payment;
import com.example.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/*  @RestController与@Controller的区别：
    1. Controller, RestController的共同点：都是用来表示Spring某个类的是否可以接收HTTP请求。
    2. Controller, RestController的不同点：
    @Controller：标识一个Spring类是Spring MVC controller处理器；
    @RestController：@RestController是@Controller和@ResponseBody的结合体，两个标注合并起来的作用。
    @Controller类中的方法可以直接通过返回String跳转到jsp、ftl、html等模版页面，
    在方法上加@ResponseBody注解，也可以返回实体对象；
    @RestController类中的所有方法只能返回String、Object、Json等实体对象，不能跳转到模版页面。
 */
@RestController//注意不是@Controller，因为结果是JSON格式
@Slf4j  //打印日志
public class OrderFeignController {

    @Resource   //服务接口
    private PaymentFeignService paymentFeignService;

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        return paymentFeignService.getPaymentById(id);
    }

    //故意写暂停程序，会报错(即耗时将超过3秒钟，但是OpenFeign默认等待1秒钟，超过后报错)
    /*
        默认Feign客户端只等待一秒钟，但是服务端处理需要超过1秒钟，导致Feign客户端不想等待了，
        直接返回报错。为了避免这样的情况，有时候我们需要设置Feign客户端的超时控制，在yml文件中设置:ReadTimeout。
     */
    @GetMapping(value = "/consumer/payment/feign/timeout")
    public String paymentFeignTimeOut(){
        //openfeign-ribbon，客户端一般默认等待1秒钟，此时故意暂停3秒钟
        return paymentFeignService.paymentFeignTimeOut();
    }
}