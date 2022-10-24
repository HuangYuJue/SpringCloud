package com.example.springcloud;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

//访问页面：http://localhost:9002/hystrix
@SpringBootApplication
@EnableHystrixDashboard //开启仪表盘图形化操作界面功能
//用于监控所有Provider微服务提供类(8001/8002/8003)
public class HystrixDashboardMain9001 {
    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardMain9001.class,args);
    }
/**
 * 注意：用于监控所有Provider微服务提供类(eg：8001/8002/8003)
 * ①被监控的微服务项目需要在pom中添加actuator控制信息完善配置：spring-boot-starter-actuator；
 * ②只要在自己的项目的启动类里配置上下面的servlet就可以了：
 *     @Bean
 *     public ServletRegistrationBean getServlet() {
 *         HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
 *         ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
 *         registrationBean.setLoadOnStartup(1);
 *         registrationBean.addUrlMappings("/hystrix.stream");
 *         registrationBean.setName("HystrixMetricsStreamServlet");
 *         return registrationBean;
 *     }
 * 运行：
 * 1、输入地址访问页面：http://localhost:9002/hystrix访问Hystrix Dashboard主页；
 * 2、在框中输入：http://localhost:8001/hystrix.stream监控PaymentHystrixMain8001；
 * 3、启动PaymentHystrixMain8001项目访问地址：http://localhost:8001/payment/circuit/8；
 * 4、点击监控页面点击Monitor Stream进行监控，之后可访问8001项目来监控。
 * 各色的数字 : 绿色：成功数，蓝色：熔断数，青色：错误请求数，橙色：超时数，紫色：线程池拒绝数，红色：失败/异常数，
 * 灰色百分比：最近10s错误百分比，黑色每秒状态：服务请求频率，当不出错时：circuit(短路状态)是绿色的closed，当出错时是红色的open；
 */
}
