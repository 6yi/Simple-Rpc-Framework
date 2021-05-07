package cn.lzheng.rpc.transport.Netty.client;

import cn.lzheng.rpc.entity.RpcResponse;
import cn.lzheng.rpc.enumeration.RpcError;
import cn.lzheng.rpc.exception.RpcException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName CacheRequest
 * @Author 6yi
 * @Date 2021/5/7 0:25
 * @Version 1.0
 * @Description: 缓存没有处理的任务
 */


public class CacheRequest {

    private static ConcurrentHashMap<String, CompletableFuture<RpcResponse>> unprocessedFutures = new ConcurrentHashMap<>();

    private volatile static CacheRequest cacheRequest;

    private CacheRequest() {
    }

    public static CacheRequest getCacheRequest() {
        if(cacheRequest==null){
            synchronized (CacheRequest.class){
                if(cacheRequest==null) cacheRequest=new CacheRequest();
            }
        }
        return cacheRequest;
    }

    public void add(String requestId, CompletableFuture<RpcResponse> future){
        unprocessedFutures.put(requestId, future);
    }
    public void remove(String requestId){
        unprocessedFutures.remove(requestId);
    }
    public void complete(RpcResponse rpcResponse){
        CompletableFuture<RpcResponse> future = unprocessedFutures.get(rpcResponse.getRequestId());
        if(null!=future){
            future.complete(rpcResponse);
        }else{
            throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE);
        }
    }

}
