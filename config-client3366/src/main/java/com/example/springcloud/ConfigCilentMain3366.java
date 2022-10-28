package com.example.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/*每次修改GitHub中文件的内容后在cmd中执行：
curl -X POST "http://localhost:3344/actuator/bus-refresh"，
即可通过地址刷新得到修改后的内容，不用再重新启动服务。
===================以上是全部通知，以下是单独通知===================
每次修改GitHub中文件的内容后在cmd中执行(需要单独通知哪个就写哪个，没写的不做改变，不接通知)：
curl -X POST "http://localhost:3344/actuator/bus-refresh/config-client:3366"，
即通知的客户端可通过地址刷新得到修改后的内容，不用再启动服务，而未通知地址的刷新不会更改。
 */
/**
 * Bus总线的的两种设计思想：
 * ①利用消息总线触发一个客户端/bus/refresh，而刷新所有客户端的配置；
 * ②利用消息总线触发一个服务端ConfigServer的/bus/refresh端口，而刷新所有的客户端的配置。
 * 本次使用了第二种，在仓库文件修改之后，config-server获取，然后再发送给各个客户端，
 * 公式：http://localhost:配置中心的端口号/actuator/bus-refresh。
 * 也可动态刷新定点通知：
 * 公式：http://localhost:配置中心的端口号/actuator/bus-refresh/{destination}
 * 不想全部通知，只想定点通知：只通知3366，不通知3355；简单一句话：指定具体某一个实例生效而不是全部。
 * 其中{destination}是：yml文件中spring.application.name:yml文件中server.port；
 * 即在本项目的yml文件中{destination}是：config-client:3366
 */
@SpringBootApplication
@EnableEurekaClient
public class ConfigCilentMain3366 {
    public static void main(String[] args) {
        SpringApplication.run(ConfigCilentMain3366.class,args);
    }
}
