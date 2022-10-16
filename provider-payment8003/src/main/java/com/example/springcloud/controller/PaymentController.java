package com.example.springcloud.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@Slf4j
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;

    @RequestMapping(value = "/payment/zk")
    public String paymentzk(){
        return "springcloud with zookeeper: "+serverPort+"\t"+ UUID.randomUUID().toString();
        //springcloud with zookeeper: 8003 b17fe53c-22ee-4fde-ab65-207160d4bafc，最后一部分每次刷新都不一样
    }
    /*
        zookeeper启动：到zookeeper的bin目录下输入：./zkServer.sh start；
        进入zookeeper：到zookeeper的bin目录下输入：./zkCli.sh；
        在VM虚拟机/XShell上：ls / 可看到根目录下多了一个services，
        在services下可看到有一个cloud-provider-payment，进入后可看到每次都不一样的编号
     */
}
