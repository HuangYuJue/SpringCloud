package com.example.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 运用nacos，只需要在nacos中的配置管理的配置列表选择配置文件进行修改发布，再刷新客户端访问链接即可获取刷新配置内容。
 */
@SpringBootApplication
@EnableDiscoveryClient  //开启注册服务
public class NacosConfigClientMain3377 {
    public static void main(String[] args) {
        SpringApplication.run(NacosConfigClientMain3377.class,args);
    }
}
