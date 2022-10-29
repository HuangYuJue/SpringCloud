package com.example.springcoud.service.impl;

import com.example.springcoud.service.IMessageProvider;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * 这个service层不同于平常的service层(平常的 service层需要引入注解 @Service)，
 * 这个service层是跟RabbitMQ打交道的，需要注释@EnableBinding(Source.class)，
 * 定义消息生产者的发送管道/消费者的接收管道，指信道channel和exchange绑定在一起。
 */
@EnableBinding(Source.class)  //定义消息的推送(发送)管道，消息生产者里是Source，消息接收者里是Sink
public class MessageProviderImpl implements IMessageProvider {

    @Resource
    private MessageChannel output;  //消息的发送管道

    //消息的发送者
    @Override
    public String send() {
        //发一个流水号
        String serial = UUID.randomUUID().toString();
        // 通道上面的消息用message绑定器绑定起来，其中build构建出了一个消息
        // 整体而言：MessageBuilder.withPayload(serial).build()是一个message
        output.send(MessageBuilder.withPayload(serial).build());
        System.out.println("******serial: "+serial);
        //******serial: a902b8d0-4291-4859-b493-307bc9a3c1da
        //******serial: 4770f555-9806-4194-bdbe-3bd1b38be136
        return null;
        /*注意：
        ①send()方法发的是String类型的信息，所以接收消息也是String，即Message<String> message；
        ②信息中发的时候用的是withPayload()方法，所以收的时候是getPayload()方法；
         */
    }
}
