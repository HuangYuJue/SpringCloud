package com.example.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {
    @Bean
    @LoadBalanced   //负载均衡，nacos中包含ribbon，对于ribbon需要加上负载均衡注解@LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
