package cn.lzheng.rpc.transport.Netty.server;

import cn.lzheng.rpc.entity.RpcRequest;
import cn.lzheng.rpc.entity.RpcResponse;
import cn.lzheng.rpc.handler.InvokeMethodHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName NettyRequestHandler
 * @Author 6yi
 * @Date 2021/5/5 19:28
 * @Version 1.0
 * @Description:
 */


public class NettyRequestHandler extends SimpleChannelInboundHandler<RpcRequest> {
    private static final Logger logger = LoggerFactory.getLogger(NettyRequestHandler.class);

    private static InvokeMethodHandler requestHandler;

    public NettyRequestHandler(InvokeMethodHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest msg) throws Exception {
        try{
            if(msg.getHeartBeat()!=null && msg.getHeartBeat()) {
                logger.info("接收到客户端心跳包...");
                return;
            }
            logger.info("服务器接收到请求: {}", msg);
            Object result = requestHandler.handle(msg);
            if(channelHandlerContext.channel().isActive() && channelHandlerContext.channel().isWritable()){
                channelHandlerContext.writeAndFlush(RpcResponse.success(result, msg.getRequestId()));
            }else{
                logger.error("通道不可写");
            }
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("处理过程调用时有错误发生:");
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.READER_IDLE) {
                logger.info("长时间未收到心跳包，断开连接...");
                ctx.close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

}
