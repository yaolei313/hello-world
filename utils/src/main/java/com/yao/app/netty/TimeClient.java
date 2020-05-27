package com.yao.app.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public class TimeClient {

    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 8080;

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap(); // (1) client侧的
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new TimeDecoder(), new TimeClientHandler());
                }
            });

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync(); // (5)

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static class TimeClientHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            ByteBuf m = (ByteBuf) msg; // (1)
            try {
                // readUnsignedInt在没有收到4个字节数据前，会抛出IndexOutOfBoundsException，故需要TimeClientHandler2的形式
                long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
                System.out.println(new Date(currentTimeMillis));
                ctx.close();
                LocalDate.now().plusDays(1).atStartOfDay();
            } finally {
                m.release();
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }

    public static class TimeClientHandler2 extends ChannelInboundHandlerAdapter {

        private ByteBuf buf;

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) {
            buf = ctx.alloc().buffer(4); // (1)
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) {
            buf.release(); // (1)
            buf = null;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            ByteBuf m = (ByteBuf) msg;
            buf.writeBytes(m); // (2)  处理fragmentation issue，即碎片问题。但是对于长度不确定的协议不友好，故需要TimeDecoder+TimeClientHandler
            m.release();

            if (buf.readableBytes() >= 4) { // (3)
                long currentTimeMillis = (buf.readUnsignedInt() - 2208988800L) * 1000L;
                System.out.println(new Date(currentTimeMillis));
                ctx.close();
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }

    /**
     * 问题：
     *
     * <p>stream-based的协议，比如TCP/IP传输协议，收到的消息都会被存储在buffer中，但底层的buffer是bytes queue，而不是packets queue. </p>
     * <p>
     * It means, even if you sent two messages as two independent packets, an operating system will not treat them as two messages but as just a bunch of bytes.
     * Therefore, there is no guarantee that what you read is exactly what your remote peer wrote.
     * </p>
     *
     * 目的： 把字节数组分拆为消息，每次channelread尽可能多的转换消息，然后交给下一个AbstractChannelHandlerContext继续处理
     */
    public static class TimeDecoder extends ByteToMessageDecoder {

        /**
         * CodecOutputList是Netty定制的一个特殊列表，该列表在线程中被缓存，可循环使用来存储解码结果，减少不必要的列表实例创建，从而提升性能。 由于解码结果需要频繁存储，普通的ArrayList难以满足该需求，故定制化了一个特殊列表
         *
         * isSingleDecode默认是false，故out是一个list，每分拆一个out，就add一下。若改为true，则会有性能问题。
         */
        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)
            if (in.readableBytes() < 4) {
                return; // (3)
            }

            //
            out.add(in.readBytes(4)); // (4)
        }
    }

    public class TimeDecoder2 extends ReplayingDecoder<Void> {

        @Override
        protected void decode(
            ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
            out.add(in.readBytes(4));
        }
    }

    /**
     * ReplayingDecoder用来对比用
     */
    public static class IntegerHeaderFrameDecoder extends ByteToMessageDecoder {

        @Override
        protected void decode(ChannelHandlerContext ctx,
            ByteBuf buf, List<Object> out) throws Exception {
            if (buf.readableBytes() < 4) {
                return;
            }

            buf.markReaderIndex();
            int length = buf.readInt();

            if (buf.readableBytes() < length) {
                buf.resetReaderIndex();
                return;
            }

            out.add(buf.readBytes(length));
        }
    }

    /**
     * 去读ReplayingDecoder的注释和代码，https://netty.io/wiki/user-guide-for-4.x.html
     */
    public static class IntegerHeaderFrameDecoder2 extends ReplayingDecoder<Void> {

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
            int size = buf.readInt();
            out.add(buf.readBytes(size));
        }
    }
}
