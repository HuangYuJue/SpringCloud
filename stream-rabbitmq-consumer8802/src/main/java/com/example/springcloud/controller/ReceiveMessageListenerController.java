package com.example.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Sink.class)  //定义消息生产者的发送管道/消费者的接收管道，消息生产者里是Source，消息接收者里是Sink，指信道channel和exchange绑定在一起。
public class ReceiveMessageListenerController {

    @Value("${server.port}")
    private String serverPort;

    @StreamListener(Sink.INPUT) //监听队列，用于消费者的队列的消费接收，输入源
    public void input(Message<String> message){
        System.out.println("消费者1号，------>接收到的消息："+message.getPayload()+"\t  port: "+serverPort);
        //消费者1号，------>接收到的消息：4770f555-9806-4194-bdbe-3bd1b38be136	  port: 8802
        /*注意：
        ①send()方法发的是String类型的信息，所以接收消息也是String，即Message<String> message；
        ②信息中发的时候用的是withPayload()方法，所以收的时候是getPayload()方法；
         */
    }
}
