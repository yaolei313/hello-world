package com.yao.app.java.nio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2020-01-19
 */
public class IOExtUtils {

    /**
     * 会被阻塞
     *
     * 从输入流读取为阻塞操作，除非client对应socket关闭阻塞才停止
     */
    public static String readMessage(BufferedInputStream in) throws IOException {
        byte[] header = new byte[4];
        int count = in.read(header);
        if (count == -1) {
            System.out.println("end");
            return null;
        }
        // big endian, high byte first
        int size = decodeSize(header);
        if (size == 0) {
            return "";
        }

        byte[] body = new byte[size];
        in.read(body);

        return new String(body, StandardCharsets.UTF_8);
    }

    public static void writeMessage(BufferedOutputStream out, String text) throws IOException {
        if (text == null) {
            return;
        }
        int len = text.length();
        ByteBuffer buffer = ByteBuffer.allocate(4 + len);

        buffer.put(encodeSize(len));
        buffer.put(text.getBytes(StandardCharsets.UTF_8));

        System.out.println(Arrays.toString(buffer.array()));

        out.write(buffer.array(), 0, buffer.position());
        out.flush();
    }

    // tcp都是big endian, high byte first
    public static byte[] encodeSize(int size) {
        final byte[] buf = new byte[4];
        buf[0] = (byte) (0xff & (size >> 24));
        buf[1] = (byte) (0xff & (size >> 16));
        buf[2] = (byte) (0xff & (size >> 8));
        buf[3] = (byte) (0xff & (size));

        return buf;
    }

    public static int decodeSize(byte[] buf) {
        return ((buf[0] & 0xff) << 24) | ((buf[1] & 0xff) << 16) | ((buf[2] & 0xff) << 8) | ((buf[3] & 0xff));
    }

}
