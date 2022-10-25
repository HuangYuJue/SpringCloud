package com.example.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 方法二：代码中注入RouteLocator的Bean配置gateway网关路由
 */
@Configuration
public class GateWayConfig {

    /**
     * 配置了一个 id 为 route-name 的路由规则，
     * 当访问地址 http://localhost:9527/guonei时，
     * 会自动转发到地址：http://news.baidu.com/guonei。
     */
    @Bean
    public RouteLocator customRouteLocator1(RouteLocatorBuilder routeLocatorBuilder){
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        // 相当于yml中routes下的id，http://news.baidu.com/guonei，最后.build是表示构建
        // 表示访问http://localhost:9527/guonei将会转发到http://news.baidu.com/guonei外网的链接地址
        routes.route("path_route_cloud",
                r -> r.path("/guonei")
                        .uri("http://news.baidu.com/guonei")).build();
        //最后将路由地址返回
        return routes.build();
    }

    /**
     * 配置了一个 id 为 route-name 的路由规则，
     * 当访问地址 http://localhost:9527/guoji时，
     * 会自动转发到地址：http://news.baidu.com/guoji。
     */
    @Bean
    public RouteLocator customRouteLocator2(RouteLocatorBuilder routeLocatorBuilder){
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("path_route_cloud2",
                r -> r.path("/guoji")
                        .uri("http://news.baidu.com/guoji")).build();
        //最后将路由地址返回
        return routes.build();
    }
}
