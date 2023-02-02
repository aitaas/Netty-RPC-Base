package com.example.netty.rpc.scan;

import com.example.netty.rpc.proxy.RpcProxy;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * 自定义的FactoryBean，使用工厂模式为带有RpcClient的类创建代理实现类。
 * @param <T>
 */
public class RpcServiceFactory<T> implements FactoryBean<T> {
    private Class<T> interfaceType;

    public RpcServiceFactory(Class<T> interfaceType) {
        this.interfaceType = interfaceType;
    }


    @SuppressWarnings("unchecked")
    @Override
    public T getObject() {
        return (T)Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class<?>[]{interfaceType}, new RpcProxy());
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceType;
    }
}
