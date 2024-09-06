package com.yao.app.java.nio.socket;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class SelectorServer {

    public static void main(String[] args) {
        ServerSocketChannel serverSocketChannel = null;
        Selector selector = null;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(0), 10);

            SocketAddress address = serverSocketChannel.getLocalAddress();
            System.out.println("address:" + address);

            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                int readyChannels = selector.select(10000);
                if (readyChannels == 0) {
                    System.out.println("wait 10s,not connection");
                    continue;
                }
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();
                    if (key.isAcceptable()) {
                        // 接收到一个新连接
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel channel = server.accept();

                        System.out.println("connected:" + channel.hashCode());
                        channel.configureBlocking(false);
                        channel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isConnectable()) {
                        // 表示一个新连接建立
                    } else if (key.isReadable()) {
                        // 表示channel有数据就绪，可以读
                        SocketChannel channel = (SocketChannel) key.channel();

                        ByteBuffer buf = ByteBuffer.allocate(512);
                        int readBytes = channel.read(buf);
                        System.out.println("read count:" + readBytes);
                        if (readBytes > 0) {
                            buf.flip();
                            String input = new String(buf.array(), 0, readBytes);
                            System.out.println("receive message:" + input);

                            String rsp = "hello world:" + input;
                            ByteBuffer buf2 = ByteBuffer.wrap(rsp.getBytes(StandardCharsets.UTF_8));
                            channel.write(buf2);
                            System.out.println("send message:" + rsp);
                        } else {
                            //
                            channel.close();
                        }
                    } else if (key.isWritable()) {
                        // 表示channel有空闲的Buffer，可以写入数据
                        // 写事件比较特殊，触发的条件取决于写缓冲区是否有空闲，不然会进入死循环。
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (selector != null) {
                    selector.close();
                }
                if (serverSocketChannel != null) {
                    serverSocketChannel.close();
                }
            } catch (Exception e2) {

            }
        }


    }
}
