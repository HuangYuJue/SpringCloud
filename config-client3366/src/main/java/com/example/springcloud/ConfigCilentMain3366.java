package com.example.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/*每次修改GitHub中文件的内容后在cmd中执行：
curl -X POST "http://localhost:3344/actuator/bus-refresh"，
即可通过地址刷新得到修改后的内容，不用再重新启动服务。
 */
/**
 * Bus总线的的两种设计思想：
 * ①利用消息总线触发一个客户端/bus/refresh，而刷新所有客户端的配置；
 * ②利用消息总线触发一个服务端ConfigServer的/bus/refresh端口，而刷新所有的客户端的配置。
 * 本次使用了第二种，在仓库文件修改之后，config-server获取，然后再发送给各个客户端。
 */
@SpringBootApplication
@EnableEurekaClient
public class ConfigCilentMain3366 {
    public static void main(String[] args) {
        SpringApplication.run(ConfigCilentMain3366.class,args);
    }
}
