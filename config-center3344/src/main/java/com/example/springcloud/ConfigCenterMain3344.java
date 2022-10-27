package com.example.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 输入以下地址测试通过Config微服务是否可以从GitHub上获取配置内容：
 * http://config-3344.com:3344/master/config-dev.yml
 * 得到config-dev.yml中的内容则表示成功。其中：config-3344.com是因为
 * 在Windows下修改了hosts文件，增加了映射：127.0.0.1  config-3344.com。
 * /master/config-dev.yml：/{label}/{application}-{profile}.yml
 * 还有其他几种方式：①/{application}/{profile}[/{label}]：/config/dev/master
 * 或者/config/dev，②/{application}-{profile}.yml：/config-dev.yml。
 */
@SpringBootApplication
@EnableConfigServer //激活配置中心
public class ConfigCenterMain3344 {
    public static void main(String[] args) {
        SpringApplication.run(ConfigCenterMain3344.class, args);
    }
}
