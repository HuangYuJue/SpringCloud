package com.example.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope   //支持Nacos的动态刷新功能，即不需要重启服务也可实现刷新
public class ConfigClientController {

    @Value("${config.info}")
    private String configInfo;//获取服务配置中心yaml文件的config.info的内容

    @GetMapping("/config/info")
    public String getConfigInfo(){
        return configInfo;
        //nacos config center,nacos-config-client-dev.yaml,version = 1,namespace = '2c2546e3-7ac5-4723-bd5d-68397716d9ca',group = 'GROUPA'
    }

}
