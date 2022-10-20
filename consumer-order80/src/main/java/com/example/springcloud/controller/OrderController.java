package com.example.springcloud.controller;
import com.example.springcloud.entities.CommonResult;
import com.example.springcloud.entities.Payment;
import com.example.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

// Ribbon = 负载均衡 + RestTemplate
@RestController
@Slf4j
public class OrderController {

    //定义需要调用的地址8001
    // 单机版
    //public static final String PAYMENT_URL = "http://localhost:8001";
    // 集群版，通过在eureka上注册过的微服务名称调用(通过在物业公司注册的公司名称查找)
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Resource//注入
    private RestTemplate restTemplate;

    @Resource//注入手写的负载均衡算法
    private LoadBalancer loadBalancer;
    @Resource//引入发现
    private DiscoveryClient discoveryClient;

    @GetMapping("/consumer/payment/lb")
    public String getPaymentLB(){
        //获取服务实例的list(有几个提供者)
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances == null || instances.size() <= 0){
            return null;
        }
        //否则则根据手写的负载均衡算法返回的服务实例来获取服务
        //调用LoadBalancer接口中的方法返回具体的实例对象
        ServiceInstance serviceInstance = loadBalancer.instances(instances);
        //根据返回的实例对象获取到uri
        URI uri = serviceInstance.getUri();
        System.out.println(uri);//http://192.168.209.185:8001或者http://192.168.209.185:8001
        //因为用定义的PAYMENT_URL时是用了配置类的@LoadBalanced负载均衡，这里是手动的负载均衡
        return restTemplate.getForObject(uri+"/payment/lb",String.class);
    }

    //因为是客户端，所以一般发的都是get请求
    @GetMapping(value = "/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        //调用restTemplate中的post请求
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
    }

    @GetMapping("/consumer/postForEntity/create")
    public CommonResult<Payment> create2(Payment payment){
        ResponseEntity<CommonResult> entity = restTemplate.postForEntity(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
        //2xx即200是成功
        if (entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        }else {
            return new CommonResult<>(444,"操作失败");
        }
    }

    //因为是客户端，所以一般发的都是get请求
    //返回对象为响应体中数据转化成的对象，基本上可以理解为Json：restTemplate.getForObject()
    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        //调用restTemplate中的get请求
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }

    //返回对象为ResponseEntity对象，包含了响应中的一些重要信息，比如响应头、响应状态码、响应体等：restTemplate.getForEntity()
    @GetMapping(value = "/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        //2xx即200是成功
        if (entity.getStatusCode().is2xxSuccessful()){
            //如果想要知道详细信息可以通过log方式
            log.info("状态码："+entity.getStatusCode());
            //返回请求体
            return entity.getBody();
        }else {
            return new CommonResult<>(444,"操作失败");
        }
    }
    /*
    IRule：根据特定算法中从服务列表中选取一个要访问的服务
     */
}
