package com.example.netty.rpc.transport;

/**
 * rpc服务器通用接口
 */
public  interface  Server {
    void start() throws Exception;

     void stop() throws Exception;

}
