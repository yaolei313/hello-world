package com.yao.app.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {

    public static void main(String[] args) {
        ServerSocketChannel serverSocketChannel = null;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(0));
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
                    int rbytes = socketChannel.read(buf);
                    while (rbytes != -1) {
                        System.out.println("read " + rbytes);
                        if (rbytes == 0) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            buf.flip();
                            sb.append(new String(buf.array(), 0, rbytes));
                            buf.clear();
                        }
                        rbytes = socketChannel.read(buf);
                    }

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
        socket.setTcpNoDelay(true);
        socket.setSoLinger(false, 0);
    }

}
