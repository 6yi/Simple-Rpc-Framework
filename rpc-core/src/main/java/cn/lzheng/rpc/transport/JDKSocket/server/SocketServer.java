package cn.lzheng.rpc.transport.JDKSocket.server;

import cn.lzheng.rpc.factory.ThreadPoolFactory;
import cn.lzheng.rpc.handler.RequestHandler;
import cn.lzheng.rpc.provider.ServiceProviderImpl;
import cn.lzheng.rpc.registry.Redis.RedisRegistry;
import cn.lzheng.rpc.serializer.CommonSerializer;
import cn.lzheng.rpc.transport.AbstractRpcServer;
import cn.lzheng.rpc.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
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
    private final CommonSerializer serializer;
    private final RequestHandler requestHandler = new RequestHandler();
    private final ExecutorService threadPool;

    public SocketServer(String host,int port) {
        this(host,port,CommonSerializer.KRYO_SERIALIZER);
    }
    public SocketServer(String host,int port,Integer serializer){
        this.host = host;
        this.port = port;
        threadPool = ThreadPoolFactory.createDefaultThreadPool("socket-rpc-server");
        this.serviceRegistry = new RedisRegistry();
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
                RedisUtil.clearRegistry();
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
