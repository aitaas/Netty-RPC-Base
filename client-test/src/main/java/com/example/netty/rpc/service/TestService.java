package com.example.netty.rpc.service;

import com.example.netty.rpc.annotation.RpcClient;
import com.example.netty.rpc.annotation.RpcReference;
import com.example.netty.rpc.entity.User;
import com.example.netty.rpc.loadbance.LoadBlance;
import com.example.netty.rpc.transport.client.ResponseFuture;

@RpcClient
public interface TestService {

    @RpcReference(serviceName = "getUser",loadBlance = LoadBlance.IP_HASH)
    User getUser(Integer id, String name, String password);

    @RpcReference(serviceName = "updateUser", loadBlance = LoadBlance.RANDOM)
    int updateUser(User user);

    @RpcReference(serviceName = "async",loadBlance = LoadBlance.RANDOM)
    ResponseFuture<String> async();
}
