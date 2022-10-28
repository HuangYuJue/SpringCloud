package com.example.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope   //实例热加载，实现刷新功能
@RestController
public class ConfigClientController {

    @Value("${config.info}")
    private String configInfo;
    /*因为在GitHub里配置文件config-dev.yml中有信息：
        config:
          info: "master branch,springcloud-config/config-dev.yml version=1"
      而@Value注解可以根据属性名获取配置文件中的值，所以如果可以通过@Value("${config.info}")
      获取到GitHub里仓库中config-dev.yml的config.info的值则表示成功，类似于支付端provider里
      的用@Value("${server.port}")来获的服务器provider对应的端口号。
     */

    @Value("${server.port}")
    private String serverPort;//3366

    @GetMapping("configInfo")
    public String getConfigInfo(){
        // 返回config.info，因为在bootstrap.yml中配置了config-dev.yml，所以查询它
        // 以及bootstrap.yml中的server.port=3366
        return "serverPort: "+serverPort+"\t\n\n configInfo: "+configInfo;
    }

}
