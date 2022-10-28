package com.example.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 输入：http://localhost:3355/configInfo返回GitHub里仓库中config-dev.yml里的config.info，
 * master branch,springcloud-config/config-dev.yml version=1
 */
/*每次修改GitHub中文件的内容后在cmd中执行：
curl -X POST "http://localhost:3355/actuator/refresh"，
即可通过地址刷新得到修改后的内容。
 */
@SpringBootApplication
@EnableEurekaClient
public class ConfigClientMain3355 {
    public static void main(String[] args) {
        SpringApplication.run(ConfigClientMain3355.class,args);
    }
}
