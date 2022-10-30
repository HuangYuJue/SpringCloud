package com.example.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 启动nacos并输入网址http://localhost:8848/nacos登录进入，再启动本工程6001，
 * 如果一切顺利，将会按照yml文件中的配置将6001注册进8848，在nacos的服务管理中服务列表里可以看到本项目服务名。
 */
@SpringBootApplication
@EnableDiscoveryClient  // 开启服务发现功能
public class NacosProviderMain6001 {
    public static void main(String[] args) {
        SpringApplication.run(NacosProviderMain6001.class,args);
    }
}
