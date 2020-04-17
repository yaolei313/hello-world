package com.yao.app.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * the channelActive() method will be invoked when a connection is established and ready to generate traffic.
     * 连接被建立，且可以收发数据时调用
     *
     * @param ctx
     */
    @Override
    public void channelActive(final ChannelHandlerContext ctx) { // (1)
        final ByteBuf time = ctx.alloc().buffer(4); // (2)

        // 2208988800为1900年1月1日00:00:00~1970年1月1日00:00:00的总秒数
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

        // ChannelHandlerContext.write() (and writeAndFlush()) method returns a ChannelFuture
        // ChannelFuture代表一个尚未发生的I/O操作，因为该操作是异步的，故状态不确定，实际可能IO已发生，可能也IO未发生。
        // 所以，在ChannelFuture完成后，我们需要close下ctx，close也是个异步操作
        final ChannelFuture f = ctx.writeAndFlush(time); // (3)
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                assert f == future;
                ctx.close();
            }
        }); // (4)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
