package com.example.springcloud.lb;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//进程内LB:将LB逻辑集成到消费方，消费方从服务注册中心获知到有哪些地址可用，然后自己再从这些地址中选择一个合适的服务器。
//Ribbon就属于进程内LB，他只是一个类库，集成于消费方进程，消费方通过它来获取到服务提供方的地址。总之：负载均衡+RestTemplate调用
/*
@Configuration 和 @Component 区别：
@Configuration 中所有带 @Bean 注解的方法都会被动态代理（cglib），因此调用该方法返回的都是同一个实例。
而 @Component 修饰的类不会被代理，每实例化一次就会创建一个新的对象，不会为其生成 CGLIB 代理 Class。
 */
@Component
public class MyLB implements LoadBalancer{
    //定义初始值为0(多线程下保证安全，记录下访问次数，并算出返回的服务器下标)
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    //坐标，final为了线程安全
    public final int getAndIncrement(){
        //存储当前rest接口访问请求的次数
        int current;
        int next;
        do {
            //当前值等于this.atomicInteger的值
            current = this.atomicInteger.get();
            //防止int溢出，2147483647是32位操作系统中最大的符号型整型常量(在test中有测试)
            next = current > 2147483647 ? 0 : current + 1;
        }while (!this.atomicInteger.compareAndSet(current,next));//CAS
        // 将当前值this.atomicInteger的内存值与预期值current比较，如果相等则compareAndSet的值为true，
        // 如果不相等返回false则什么都不改变，如果相等返回true，
        // 然后修改this.atomicInteger的内存值为next，整个结果为true再取反为false跳出循环。
        /*
        compareAndSet方法实际上是做了两部操作：
        第一是比较：将预期值expect与atomicInteger的内存值进行比较是否相同，
        public final boolean compareAndSet(int expect, int update)；value的值为expect的值；
        第二是把value的值expect更新为update；注意，如果第一步比较不相等返回false时，什么都不改变。
        这两步是原子操作，在没有多线程锁的情况下，借助cpu锁保证数据安全
         */
        System.out.println("********第"+next+"次访问********");
        return next;
    }

    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        //得到next并进行 % ServiceInstance服务实例数 = 本次服务器下标
        int index = getAndIncrement() % serviceInstances.size();
        System.out.println(serviceInstances.get(index));//得到下标为index的服务器
        //org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient$EurekaServiceInstance@7429f5c最后一部分@...每次都不相同
        return serviceInstances.get(index);
    }
}
