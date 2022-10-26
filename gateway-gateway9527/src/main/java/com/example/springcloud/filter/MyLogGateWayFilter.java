package com.example.springcloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
@Slf4j
public class MyLogGateWayFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        /*
        输入：http://localhost:9527/payment/lb?uname=zs时：前台随机返回8001、8002，非轮询，是随机，
        控制台返回********come in MyLogGateWayFilter 我的全局过滤器 : Wed Oct 26 17:35:12 CST 2022；
        输入：http://localhost:9527/payment/lb(没带uname)时：前台无响应，
        控制台返回********come in MyLogGateWayFilter 我的全局过滤器 : Wed Oct 26 17:37:38 CST 2022，
        以及******用户名为null，非法，非法用户，不允许放行******
         */
        System.out.println("********come in MyLogGateWayFilter 我的全局过滤器 : "+new Date());
        //获取对象的名字
        String uname = exchange.getRequest().getQueryParams().getFirst("uname");

        if (uname == null){
            log.info("******用户名为null，非法，非法用户，不允许放行******");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
