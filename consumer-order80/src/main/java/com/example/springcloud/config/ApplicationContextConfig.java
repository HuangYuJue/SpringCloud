package com.example.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {
    @Bean//用注解的方式实现spring中的依赖注入
    //如果自定义LoadBalancer负载均衡算法，则需要注释掉
    //@LoadBalanced//集群版需加入，赋予RestTemplate负载均衡的能力
    /**
     * @LoadBalanced:一个服务做了集群之后如果不加这个注解可能会找不到哪一个服务(server.port)给我运行，
     * 所以必须加这个注解让我的服务具有负载均衡的能力，能在调用其他微服务的时候，
     * 通过服务实例名称就能进行调用其他的微服务，而不是直接把要调用的微服务的ip和端口号写死在controller代码当中。
     * 这里要声明一下：默认的负载均衡的机制是轮询就是一个服务一次轮着来；
     * 首先：这个@LoadBalanced注解是来自cloud包下的一个注解，这个注解就是让某一个东西拥有负载均衡的能力。
     * 这里就是让这个RestTemplate在请求时拥有客户端负载均衡的能力 ：RestTemplate 这个可以理解成为客服端。
     */
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
    /**
     * *RestTemplate是由Spring框架提供的一个可用于应用中调用rest服务的类，它简化了与http服务的通信方式，
     *  是Spring提供的一个调用Restful服务的抽象层，统一了RESTFul的标准，封装了http连接，
     *  我们只需要传入url及其返回值类型即可。相较于之前常用的HttpClient，RestTemplate是一种更为优雅的调用RESTFul服务的方式。
     * *在Spring应用程序中访问第三方REST服务与使用Spring RestTemplate类有关。RestTemplate类的设计原则
     *  与许多其他Spring的模板类(例如JdbcTemplate)相同，为执行复杂任务提供了一种具有默认行为的简化方法。
     * *RestTemplate默认依赖JDK提供了http连接的能力（HttpURLConnection），如果有需要的话
     *  也可以通过setRequestFactory方法替换为例如Apache HttpCompoent、Netty或OKHttp等其他Http libaray。
     *  考虑到了RestTemplate类是为了调用REST服务而设计的，因此它的主要方法与REST的基础紧密相连就不足为奇了，
     *  后者时HTTP协议的方法：HEAD、GET、POST、PUT、DELETE、OPTIONS，例如，
     *  RestTemplate类具有headForHeaders()、getForObject()、putForObject()，put()和delete()等方法。
     * *创建RestTemplate：
     *  因为RestTemplate是Spirng框架提供的所以只要是一个Springboot项目就不用考虑导包的问题，这些都是提供好的。
     *  但是Spring并没有将其加入SpringBean容器中，需要我们手动加入，因为我们首先创建一个Springboot配置类，
     *  再在配置类中将我们的RestTemlate注册到Bean容器中
     */
    /*
    rest服务是一种Web服务架构，其目标是为了创建具有良好扩展性的分布式系统。
rest应该具备以下条件：
    使用客户/服务器模型(简称C/S结构，是一种网络架构，它把客户端 (Client) 与服务器(Server) 区分开来。
    每一个客户端软件的实例都可以向一个服务器或应用程序服务器发出请求。)
    例如前后端分离，页面和服务不在同一服务器上运行。
    层次化的系统
    例如一个父系统下有多个子模块，每个模块都是独立的服务。
    无状态
    服务端并不会保存有关客户的任何状态，也就是说要服务后端服务，就要带token过去。
    可缓存
    例如服务端通过token缓存已登录过的用户信息，客户端请求会带一个token过来，
    后台服务通过带过来的token在缓存中取出用户信息，提高效率。
    统一的接口
    例如，一个项目的所有模块都整合到一起，all-in-one，打成一个包，多个服务，整合到一个端口下。
如果一个系统满足了上面所列出的五条约束，那么该系统就被称为是RESTful的(一种软件架构风格、设计风格，而不是标准，
只是提供了一组设计原则和约束条件。它主要用于客户端和服务器交互类的软件。基于这个风格设计的软件可以更简洁，更有层次，更易于实现缓存等机制。)
     */
}
