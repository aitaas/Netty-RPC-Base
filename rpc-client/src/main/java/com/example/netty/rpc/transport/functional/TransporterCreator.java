package com.example.netty.rpc.transport.functional;

import com.example.netty.rpc.transport.client.Transporter;


/**
 * 连接创建
 */
public interface TransporterCreator {
    /**
     * 创建一个新的连接
     * @return
     */
    Transporter createTransporter(String host, int port);
}
