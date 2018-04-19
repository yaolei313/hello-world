package com.yao.app.java.nio.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server1 {

    private static Logger logger = LoggerFactory.getLogger(Server1.class);

    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            // 默认50个最大等待队列backlog
            int backlog = 2;
            server = new ServerSocket(8088, backlog);

            // 需了解backlog及tcp连接建立过程
            // 只有进入了accept queue的连接才能被accept处理，该队列大小由backlog指定。还有个syns queue,收到SYN_SEND请求就成加入该队列。
            // DDOS中SYN flood,NTP flood。 Network Time Protocol是基于UDP协议的
            while (true) {
                logger.debug("等待连接......");

                Socket socket = server.accept();

                logger.debug("有客户端连上服务端, 客户端信息如下：" + socket.getInetAddress() + " : " + socket.getPort());

                // 此处是同步的，正常需要改为异步
                ExecutorService executor = new ThreadPoolExecutor(10, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1000));
                executor.submit(new Task(socket));
            }

        } catch (Exception e) {
            e.printStackTrace();
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
            try {
                handleSocket(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        protected void handleSocket(Socket socket) throws IOException {
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));

            // 从输入流读取为阻塞操作，除非client对应socket关闭阻塞才停止。
            // 使用BufferedReader.readline要注意，因为会阻塞直到获取line的eof字符\n,\r
            StringBuilder sb = new StringBuilder();

            String line = br.readLine();
            while (line != null) {
                // System.out.println("line:" + line);
                sb.append(line);
                if (line.contains("eof")) {
                    break;
                }
                line = br.readLine();
            }


            System.out.println("from client:" + sb.toString());

            PrintWriter bw =
                    new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),
                        Charset.forName("UTF-8"))));

            bw.println("SUCCESS");
            bw.println("eof");
            bw.flush();

            bw.close();
            br.close();
            socket.close();

            logger.debug("关闭客户端连接");
        }

    }



}
