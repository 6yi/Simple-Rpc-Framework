package cn.lzheng.rpc.transport.JDKSocket.server;


import cn.lzheng.rpc.enumeration.RpcError;
import cn.lzheng.rpc.exception.RpcException;
import cn.lzheng.rpc.factory.ThreadPoolFactory;
import cn.lzheng.rpc.handler.RequestHandler;
import cn.lzheng.rpc.provider.ServiceProviderImpl;
import cn.lzheng.rpc.registry.ServiceRegistry;
import cn.lzheng.rpc.serializer.CommonSerializer;
import cn.lzheng.rpc.transport.AbstractRpcServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.*;

/**
 * @ClassName SocketRpcServer
 * @Author 6yi
 * @Date 2021/4/26 14:48
 * @Version 1.0
 * @Description:
 */


public class SocketServer extends AbstractRpcServer {


    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);
    private CommonSerializer serializer;
    private final RequestHandler requestHandler = new RequestHandler();
    private ExecutorService threadPool;

    public SocketServer(String host,String URI){
        this(host,null,CommonSerializer.DEFAULT_SERIALIZER, ServiceRegistry.DEFAULT_REGISTRY,URI);
    }

    public SocketServer(String host,Integer port,String URI) {
        this(host,port,CommonSerializer.DEFAULT_SERIALIZER, ServiceRegistry.DEFAULT_REGISTRY,URI);
    }


    public SocketServer(String host,Integer port,Integer serializer,Integer registry,String URI){
        this.host = host;
        if (port==null){
            try {
                ServerSocket serverSocket = new ServerSocket(0);
                this.port=serverSocket.getLocalPort();
                serverSocket.close();
                logger.info("随机端口号:{}",this.port);
            } catch (Exception e) {
                logger.error("无空闲端口号");
                throw new RpcException(RpcError.UNKNOWN_ERROR);
            }
        }else{
            this.port=port;
        }
        threadPool = ThreadPoolFactory.createDefaultThreadPool("socket-rpc-server");
        this.serviceRegistry = ServiceRegistry.getByCode(URI,registry);
        this.serviceProvider = new ServiceProviderImpl();
        this.serializer = CommonSerializer.getByCode(serializer);
        scanServices();  //扫描服务
    }

    @Override
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket()){
            serverSocket.bind(new InetSocketAddress(host,port));
            logger.info("rpc服务启动……");
            Runtime.getRuntime().addShutdownHook(new Thread(()->{
                this.serviceRegistry.clearRegistry();
                ThreadPoolFactory.shutDownAll();
            }));
            Socket socket;
            while((socket = serverSocket.accept()) != null){
                logger.info("消费者连接: {}:{}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new SocketRequestHandlerThread(socket, requestHandler, serializer));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            logger.error("服务器启动时有错误发生:", e);
        }
    }
}
