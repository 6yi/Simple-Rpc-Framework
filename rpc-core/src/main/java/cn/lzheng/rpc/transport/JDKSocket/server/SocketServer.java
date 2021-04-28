package cn.lzheng.rpc.transport.JDKSocket.server;

import cn.lzheng.rpc.transport.RpcServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.*;

/**
 * @ClassName SocketRpcServer
 * @Author 6yi
 * @Date 2021/4/26 14:48
 * @Version 1.0
 * @Description:
 */


public class SocketServer implements RpcServer {

    private final ExecutorService threadPool;
    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    @Override
    public void start() {

    }

    public SocketServer() {
        int corePoolSize = 5;
        int maximumPoolSize = 50;
        long keepAliveTime = 60;
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(100);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadPool = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                workingQueue,
                threadFactory);
    }

    @Override
    public <T> void publishService(T service, String serviceName) {

    }
}
