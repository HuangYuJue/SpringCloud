package com.example.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 找到rabbitmq-server.bat双击，在地址栏输入http://localhost:15672即可访问rabbitmq。
 * 消息驱动：整合rabbitmq以及Kafka中间体，类似于jdbc
 * stream-rabbitmq-provider8801为消息的发送模块；
 * stream-rabbitmq-consumer8802以及stream-rabbitmq-consumer8803为消息的接收模块。
 */
/*
点击rabbitmq中Exchanges下的studyExchange，再点击Bindings里可以查看到绑定到了目标；
如果消费者8802、8803都运行，则默认在Exchanges中的Bindings里会有两个绑定分组(还可在rabbitmq下的Queues下看到有几个绑定分组)，
如果8801发送消息，8802和8803都会接收到相同的消息，则发送的消息被两个消费者接收到(重复消费)；
eg:8801：******serial: f2fd4026-fa23-4fe5-8cf4-5bdc4da5dd5e
8802：消费者1号，------>接收到的消息：f2fd4026-fa23-4fe5-8cf4-5bdc4da5dd5e	  port: 8802
8803：消费者2号，------>接收到的消息：f2fd4026-fa23-4fe5-8cf4-5bdc4da5dd5e	  port: 8803
重复消费问题：
springcloud-stream目前案例是使用rabbitmq作为支持，现在默认的destination是topic形式，
不同的微服务实例会共享到同一个topic，这个也是因为不同的微服务实例默认是不同组的，则在Exchanges中有几个组，
不同组的微服务实例是全面消费(重复消费)；同组的微服务实例是竞争关系，能够保证消息只会被同一组的其中一个应用消费一次；
如果要避免某些服务实例重复消费（这里的重复消费是：一个订单同时被多个服务获取到），就要进行分组。
导致原因：默认分组group是不同的，组流水号不一样，被认为是不同组，表示可以消费。
解决重复消费问题：
自定义配置分组，group两个不同名组(因为默认分组名是流水号分组，此时自定义分组名分别设为consumerA和consumerB)，
自定义配置分为同一个组，group两个相同名组，并且8802、8803是轮询的消费服务。
原理：微服务放置在同一个group中，就能保证信息只会被其中一个应用消费一次。
不同组是可以消费的，同一个组会发生竞争关系，只有其中一个可以消费。
 */
@SpringBootApplication
public class StreamMQMain8803 {
    public static void main(String[] args) {
        SpringApplication.run(StreamMQMain8803.class,args);
    }
}
