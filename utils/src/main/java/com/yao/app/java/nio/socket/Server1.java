package com.yao.app.java.nio.socket;

import com.yao.app.java.nio.IOExtUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server1 {

    private static Logger log = LoggerFactory.getLogger(Server1.class);

    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            // 默认50个最大等待队列backlog
            int backlog = 2;
            server = new ServerSocket(8088, backlog);
            server.setReuseAddress(true);
            server.setSoTimeout(0);

            // 需了解backlog及tcp连接建立过程
            // 只有进入了accept queue的连接才能被accept处理，该队列大小由backlog指定。还有个syns queue,收到SYN_SEND请求就成加入该队列。
            // DDOS中SYN flood, NTP flood。Network Time Protocol是基于UDP协议的
            while (true) {
                log.debug("等待连接......");

                Socket socket = server.accept();

                log.debug("有客户端连上服务端, 客户端信息如下：" + socket.getInetAddress() + " : " + socket.getPort());

                // 此处是同步的，正常需要改为异步
                ExecutorService executor = new ThreadPoolExecutor(10, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
                executor.submit(new Task(socket));
            }
        } catch (Exception e) {
            log.error("error", e);
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                }
            }
        }
    }

    static class Task implements Runnable {

        private Socket socket;

        public Task(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            BufferedInputStream in = null;
            BufferedOutputStream out = null;

            try {
                socket.setSoLinger(false, 0);
                socket.setTcpNoDelay(true);
                socket.setKeepAlive(true);
                socket.setSoTimeout(300 * 1000);

                in = new BufferedInputStream(socket.getInputStream());
                out = new BufferedOutputStream(socket.getOutputStream());

                while (true) {
                    log.info("wait message");
                    String input = IOExtUtils.readMessage(in);
                    if (input == null) {
                        break;
                    }
                    log.info("receive message {} from client", input);
                    IOExtUtils.writeMessage(out, "hello," + input);
                }
            } catch (Exception e) {
                log.error("error", e);
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                    socket.close();
                    log.info("关闭客户端连接");
                } catch (IOException e) {

                }
            }
        }
    }

}
