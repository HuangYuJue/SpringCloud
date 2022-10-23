package com.example.springcloud.service.impl;
import com.example.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

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
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    public String paymentInfo_TimeOut(Integer id){
        //暂停5秒钟线程(超时异常)
        int timeNumber = 5;
        /*try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        //运行报错(运行异常)
        int age = 10/0;
        return "线程池： "+Thread.currentThread().getName()+" paymentInfo_TimeOut,id:  "+id+"\t"+"ovo"+"  耗时(秒): "+timeNumber;
        //线程池： http-nio-8001-exec-4 paymentInfo_TimeOut,id: 6 ovo 耗时(秒): 3
    }
    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池： "+Thread.currentThread().getName()+" 系统繁忙或者运行报错，请稍后再试,id:  "+id+"\t"+"~^~";
        //线程池： HystrixTimer-1 系统繁忙或者运行报错，请稍后再试,id: 6 ~^~
        //在控制台(controller中的log方法)输出：******result: 线程池： HystrixTimer-1 系统繁忙或者运行报错，请稍后再试,id:  6	~^~
    }

}
