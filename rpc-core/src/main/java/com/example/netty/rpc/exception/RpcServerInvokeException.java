package com.example.netty.rpc.exception;

/**
 * rpc远程执行出现异常
 */
public class RpcServerInvokeException extends RpcInvokeException {
    public RpcServerInvokeException(String message) {
        super(message);
    }
}
