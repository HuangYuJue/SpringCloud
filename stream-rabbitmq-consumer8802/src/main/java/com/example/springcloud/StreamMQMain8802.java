package com.example.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 找到rabbitmq-server.bat双击，在地址栏输入http://localhost:15672即可访问rabbitmq。
 * 消息驱动：整合rabbitmq以及Kafka中间体，类似于jdbc
 * stream-rabbitmq-provider8801为消息的发送模块；
 * stream-rabbitmq-consumer8802以及stream-rabbitmq-consumer8803为消息的接收模块。
 */
@SpringBootApplication
public class StreamMQMain8802 {
    public static void main(String[] args) {
        SpringApplication.run(StreamMQMain8802.class,args);
    }
}
