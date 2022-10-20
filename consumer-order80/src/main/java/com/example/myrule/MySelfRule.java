package com.example.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySelfRule {
    @Bean
    //IRule:根据特定算法从服务列表中选取一个要访问的服务
    public IRule myRule(){
        //定义为随机，因为默认为轮询,即RoundRobinRule
        return new RandomRule();
    }
    /**
     * 负载均衡算法：rest接口第几次请求数 % 服务器集群总数量 = 实际调用服务器位置下标；每次服务重启后rest接口计数从1开始。
     * 规定负载均衡的方式是在消费者中自定义的 MySelfRule 类中，而不在提供者中。
     * (MySelfRule不能在springcloud包中，因为默认扫描会扫描整个springcloud包，而MySelfRule不能被@SpringBootApplication中的@ComponentScan扫描)
     * List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
     * 上一行是在提供者的controller中，而不再消费者的controller中。
     * 如：List[0] instances = 127.0.0.1:8002     List[1] instances = 127.0.0.1:8001
     *    8001 + 8002 组合成为集群，它们共计2台机器，集群总数为2，按照轮询算法原理：
     *    当总请求数为1时：1 % 2 = 1对应下标位置为1，则获得服务地址为127.0.0.1:8001（list.get(index)）
     *    当总请求数为2时：2 % 2 = 0对应下标位置为0，则获得服务地址为127.0.0.1:8002（list.get(index)）
     *    当总请求数为3时：3 % 2 = 1对应下标位置为1，则获得服务地址为127.0.0.1:8001（list.get(index)）
     *    当总请求数为4时：4 % 2 = 0对应下标位置为0，则获得服务地址为127.0.0.1:8002（list.get(index)）  ....
     */
}
/*
com.netflix.loadbalancer.RoundRobinRule：轮询
com.netflix.loadbalancer.RandomRule：随机
com.netflix.loadbalancer.RetryRule：先按照RoundRobinRule(轮询)的策略获取服务，如果获取服务失败则在指定时间内会进行重试
WeightedResponseTimeRule ：对RoundRobinRule(轮询)的扩展，响应速度越快的实例选择权重越大，越容易被选择
BestAvailableRule ：会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发量最小的服务
AvailabilityFilteringRule ：先过滤掉故障实例，再选择并发较小的实例
ZoneAvoidanceRule：默认规则，复合判断server所在区域的性能和server的可用性选择服务器
 */
