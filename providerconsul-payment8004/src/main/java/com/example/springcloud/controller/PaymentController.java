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
    private String serverPort;  //导入端口号

    @RequestMapping(value = "/payment/consul")
    public String paymentConsul(){
        return "springcloud with consul: "+serverPort+"\t"+ UUID.randomUUID().toString();
        //springcloud with consul: 8004 7b1486c0-a884-49dd-82fd-27fe9c05f870l；最后一部分每次刷新都不一样
        /*
            在consul解压的文件夹下面打开cmd，并输入：consul，若出现一连串英文则表示安装成功；
            命令行输入：consul agent -dev启动consul，看到Consul agent running! 启动成功；
            通过以下地址可以访问Consul首页：http://localhost:8500；即安装运行成功。
         */
    }

}
