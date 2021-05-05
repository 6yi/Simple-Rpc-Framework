package cn.lzheng.rpc.transport.Netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @ClassName CommonEncoder
 * @Author 6yi
 * @Date 2021/5/5 16:28
 * @Version 1.0
 * @Description:
 */


public class CommonEncoder extends MessageToByteEncoder {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {

    }
}
