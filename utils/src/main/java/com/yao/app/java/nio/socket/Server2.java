package com.yao.app.java.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server2 {

    public static void main(String[] args) {
        ServerSocketChannel serverSocketChannel = null;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            // 默认50个最大等待队列backlog
            int backlog = 2;
            // 0的话会导致系统选择一个ephemeral port
            serverSocketChannel.bind(new InetSocketAddress(0), backlog);
            System.out.println(serverSocketChannel.isBlocking());
            int localPort = serverSocketChannel.socket().getLocalPort();
            System.out.println("actual port:" + localPort);

            while (true) {
                System.out.println("waiting...");
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    System.out.println("receive connection");
                    socketChannel.configureBlocking(false);
                    configure(socketChannel.socket());

                    // 读数据
                    StringBuilder sb = new StringBuilder();
                    ByteBuffer buf = ByteBuffer.allocate(128);
                    // 阻塞模式下， if a channel is in blocking mode and there is at least one byte remaining in the buffer
                    // then this method will block until at least one byte is read.
                    // read返回-1说明客户端数据调用了shutdownInput
                    // 非阻塞模式下, cannot read any more bytes than are immediately available from the socket's input buffer;
                    // similarly, a file channel cannot read any more bytes than remain in the file
                    int readBytes = socketChannel.read(buf);
                    while (readBytes != -1) {
                        System.out.println("read " + readBytes);
                        if (readBytes == 0) {
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            continue;
                        }
                        buf.flip();
                        sb.append(new String(buf.array(), 0, readBytes));
                        buf.clear();

                        readBytes = socketChannel.read(buf);
                    }
                    System.out.println("client shutdown input");

                    // 写数据
                    ByteBuffer wbuf = ByteBuffer.allocate(128);
                    wbuf.put("hello world".getBytes());
                    wbuf.flip();
                    while (wbuf.hasRemaining()) {
                        int wbytes = socketChannel.write(wbuf);
                        System.out.println("write " + wbytes);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocketChannel != null) {
                try {
                    serverSocketChannel.close();
                } catch (IOException e) {
                }
            }
        }

    }

    private static void configure(Socket socket) throws SocketException {
        // Delayed Ack(Ack确认延迟)和Nagle Algorithm
        // 启用了tcp nodelay，意思是禁用了nagle算法，允许小包的发送。对于延迟敏感型，同时数据量比较小的应用，还是可以的。
        socket.setTcpNoDelay(true);
        // So Linger等待发送缓冲区中的数据发送完成，但是并不保证发送缓冲区中的数据一定被对端接收，只是说会等待一段时间让这个过程完成。
        // 使用这个选项会导致睡眠，直到收到FIN的ACK包，或者等待超时；等待过程中收到数据包还是会发送RST包；消耗更多的资源。
        socket.setSoLinger(false, 0);
        socket.setKeepAlive(true);
    }

}
