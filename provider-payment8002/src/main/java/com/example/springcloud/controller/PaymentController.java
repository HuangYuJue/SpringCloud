package com.example.springcloud.controller;

import com.example.springcloud.entities.CommonResult;
import com.example.springcloud.entities.Payment;
import com.example.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j//打印日志
public class PaymentController {

    //调用service层
    //@Autowired//spring中的注入依赖
    @Resource//java中的注入依赖
    private PaymentService paymentService;

    // 集群版需要添加，因为注册的名称是一样的，但provider可以有几个(一个公司有几个老师，通过老师的名字找到具体的老师)
    /**
     * ①、@Value(“${xxxx}”)注解从配置文件读取值的用法，也就是从application.yaml文件中获取xxxx值。
     * ②、@Value("xiaozhou")
     *    privat String name;表示常量注入，表明name的值是xiaozhou。
     * ③、@Value(“#{}”)是获取bean属性，系统属性，表达式。
     */
    @Value("${server.port}")//读取到yml文件中的server.port，即8001
    private String serverPort;//配置找到具体的端口号

    @Resource
    private DiscoveryClient discoveryClient;
    //对于注册进eureka里面的微服务，可以通过服务发现来获得该微服务的信息

    //运行：http://localhost:8002/payment/discovery运行discovery()方法
    @GetMapping(value = "/payment/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        //通过getServices()方法，获取到已经注册到Eureka中的服务名
        for (String element : services){
            log.info("********element:"+element);
            //********element:cloud-payment-service
            //********element:cloud-order-service
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances){
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
            //CLOUD-PAYMENT-SERVICE	192.168.209.185	8001	http://192.168.209.185:8001
            //CLOUD-PAYMENT-SERVICE	192.168.209.185	8002	http://192.168.209.185:8002
        }
        return discoveryClient;
        //{"services":["cloud-payment-service","cloud-order-service"],"order":0}
    }

    @PostMapping(value = "/payment/create")//一般的浏览器不太支持post请求，可以用postman进行模拟post请求
    public CommonResult create(@RequestBody Payment payment){
        /**
         * @RequestBody主要用来接收前端传递给后端的json字符串中的数据(请求体中的数据的)并将json格式的数据转为java对象；
         * GET方式无请求体，所以使用@RequestBody接收数据时，前端不能使用GET方式提交数据，
         * 而是用POST方式进行提交。在后端的同一个接收方法里，@RequestBody与@RequestParam()可以同时使用，
         * @RequestBody最多只能有一个，而@RequestParam()可以有多个。
        什么时候要用@RequestBody注解：
        当前端传来的值，不是个完整的对象，只是包含了 Req 中的部分参数时，不需要@RequestBody；
        当前端传来的是整个对象，而且是以json格式传输，需要@RequestBody。
        ①运行：http://localhost:8001/payment/create/腾讯 时不需要加，加了报错，因为不是以json的形式传输的。
        ②运行：http://localhost:80/consumer/payment/create/腾讯 时需要加，
              不然能运行成功但是数据库中添加的值为null，id有值，但是需要添加进去的值为null，
              因为order中将数据通过CommonResult转为json格式，再发送到本方法中，本方法中运行时已经是json封装体了。
         */
        int result = paymentService.create(payment);
        //后台输出日志
        log.info("*****插入结果："+result);
        if (result > 0){
            return new CommonResult(200,"插入数据库成功，serverPort:"+serverPort,result);
        }else {
            return new CommonResult(444,"插入数据库失败",null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("*****插入结果："+payment+"测试热部署(●'◡'●)");
        if (payment != null){
            return new CommonResult(200,"查询成功，serverPort:"+serverPort,payment);
        }else {
            return new CommonResult(444,"没有对应的记录，查询的id是："+id,null);
        }
    }
}
