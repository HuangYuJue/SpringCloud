package com.example.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient //注意一定要添加，表示它是eureka中的provider(入驻公司)
@EnableDiscoveryClient  //向注册中心注册服务
/* @EnableDiscoveryClient和@EnableEurekaClient共同点就是：
    都是能够让注册中心能够发现，扫描到改服务。
   不同点：@EnableEurekaClient只适用于Eureka作为注册中心，
         @EnableDiscoveryClient可以是其他注册中心。
 */
public class PaymentMain8002 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8002.class,args);
    }
}
