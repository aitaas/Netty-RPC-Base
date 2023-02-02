package com.example.netty.rpc.listener.impl;

import com.example.netty.rpc.listener.NettyStateListener;
import com.example.netty.rpc.registry.Registry;
import com.example.netty.rpc.transport.NettyConfig;

/**
 * 服务注册监听
 */
public class NettyRegistryListener implements NettyStateListener {
    private Registry registry;

    public NettyRegistryListener(Registry registry) {
        this.registry = registry;
    }

    @Override
    public void onNettyStart(NettyConfig config) {
        // 在netty启动时，同时上线所有注册过的服务
        registry.registry();
    }

    @Override
    public void onNettyStop(NettyConfig config) {
        // 在netty关闭时，下线所有已经注册的服务
        registry.unRegistry();
    }

    @Override
    public void onException(Throwable e) {

    }
}
