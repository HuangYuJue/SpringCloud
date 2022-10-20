package com.example.springcloud.lb;
import org.springframework.cloud.client.ServiceInstance;
import java.util.List;

//手写负载均衡算法
public interface LoadBalancer {
    //得到ServiceInstance服务实例的List对象(收集服务器总共有多少台能够提供服务的机器，并放到list里面)
    ServiceInstance instances(List<ServiceInstance> serviceInstances);
}
