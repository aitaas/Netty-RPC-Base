package com.example.netty.rpc.annotation;


import com.example.netty.rpc.loadbance.LoadBlance;

import java.lang.annotation.*;

/**
 * 客户端调用服务的注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD})
public @interface RpcReference {
    /**
     * 调用的服务名称
     * @return
     */
    String serviceName();

    /**
     * 使用的负载均衡策略
     * @return
     */
    LoadBlance loadBlance() default LoadBlance.RANDOM;

    int timeout() default 500;

}
