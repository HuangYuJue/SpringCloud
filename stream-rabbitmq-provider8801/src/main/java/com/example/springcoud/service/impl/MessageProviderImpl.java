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
 * 定义消息生产者的发送管道，指信道channel和exchange绑定在一起。
 */
@EnableBinding(Source.class)  //定义消息的推送(发送)管道
public class MessageProviderImpl implements IMessageProvider {

    @Resource
    private MessageChannel output;  //消息的发送管道

    //消息的发送者
    @Override
    public String send() {
        //发一个流水号
        String serial = UUID.randomUUID().toString();
        //通道上面的消息用message绑定器绑定起来，其中build构建出了一个消息
        output.send(MessageBuilder.withPayload(serial).build());
        System.out.println("******serial: "+serial);
        //******serial: a902b8d0-4291-4859-b493-307bc9a3c1da
        return null;
    }
}
