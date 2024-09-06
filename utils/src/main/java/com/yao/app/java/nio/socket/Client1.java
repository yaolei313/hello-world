package com.yao.app.java.nio.socket;

import com.yao.app.java.nio.IOExtUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client1 {

    public static void main(String[] args) {
        try (Socket socket = new Socket()) {
            // 8s连接超时
            socket.connect(new InetSocketAddress("127.0.0.1", 54398), 8000);
            // 默认情况下的socket.close()会立即返回，但是底层的socket并没有立即关闭，他会延迟一段时间，等待发送完毕后再关闭。
            // (true,n)的话，会在两种情况关闭，数据发送完毕，或者等待超过n秒
            socket.setSoLinger(false, 0);
            socket.setTcpNoDelay(true);
            // 长时间处于空闲状态的socket，是否自动关闭
            socket.setKeepAlive(true);
            // 设置读取超时时间，仅表示对于socket.read() 最大阻塞时间。
            socket.setSoTimeout(300 * 1000);

            BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
            BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());

            IOExtUtils.writeMessage(out, "li bai");
            String response = IOExtUtils.readMessage(in);
            System.out.println("from server:" + response);

            IOExtUtils.writeMessage(out, "zhang san");
            response = IOExtUtils.readMessage(in);

            System.out.println("from server:" + response);

            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
