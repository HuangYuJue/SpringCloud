package com.example.springcloud.service.impl;
import cn.hutool.core.util.IdUtil;
import com.example.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl implements PaymentService {

    //正常访问，一定OK
    public String paymentInfo_OK(Integer id){
        return "线程池： "+Thread.currentThread().getName()+" paymentInfo_OK,id:  "+id+"\t"+"ovo";
        //线程池： http-nio-8001-exec-8 paymentInfo_OK,id: 6 ovo
    }

    //模拟睡眠5秒钟/运行报错
    /*
    服务降级provider端fallback：
        @HystrixCommand注解是方法级别的，在需要捕获的方法上加上注解：
        fallbackMethod：标记的是捕获异常时需要执行的方法，方法名称跟value值要一样，
        表示如果paymentInfo_TimeOut方法出事了，由paymentInfo_TimeOutHandler方法给他兜底。
        commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")}
        表示timeoutInMilliseconds线程的超时时间是3000(3秒钟)，超过3秒钟则服务降级，调用备选兜底方法。
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    })
    public String paymentInfo_TimeOut(Integer id){
        //暂停3秒钟线程(超时异常)
        int timeNumber = 3;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //运行报错(运行异常)
        //int age = 10/0;
        return "线程池： "+Thread.currentThread().getName()+" paymentInfo_TimeOut,id:  "+id+"\t"+"ovo"+"  耗时(秒): "+timeNumber;
        //线程池： http-nio-8001-exec-4 paymentInfo_TimeOut,id: 6 ovo 耗时(秒): 3
    }
    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池： "+Thread.currentThread().getName()+" 系统繁忙或者运行报错，请稍后再试,id:  "+id+"\t"+"~^~";
        //线程池： HystrixTimer-1 系统繁忙或者运行报错，请稍后再试,id: 6 ~^~
        //在控制台(controller中的log方法)输出：******result: 线程池： HystrixTimer-1 系统繁忙或者运行报错，请稍后再试,id:  6	~^~
    }

    //=============以下是服务熔断，以上是服务降级=============//
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"), //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数，发送10次请求
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//时间窗口期，10s
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),//请求失败率达到多少后跳闸，60%，即10次里面有6次失败
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if (id < 0){
            throw new RuntimeException("******id 不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();//等于UUID.randomUUID().toString()
        return Thread.currentThread().getName()+"\t"+"调用成功，流水号："+serialNumber;
        //hystrix-PaymentServiceImpl-1 调用成功，流水号：685f08263a504a138d9b0d6217ae765e
    }
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id 不能负数，请稍后再试，~_~ id: "+id;
        //id 不能负数，请稍后再试，~_~ id: -8
    }

}
