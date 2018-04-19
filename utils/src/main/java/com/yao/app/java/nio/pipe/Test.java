package com.yao.app.java.nio.pipe;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class Test {

    public static void main(String[] args) {
        try {
            Pipe pipe = Pipe.open();
            Thread t1 = new Thread(new MessageOutput(pipe));
            Thread t2 = new Thread(new MessageInput(pipe));

            t2.start();
            Thread.sleep(1000);
            t1.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static class MessageOutput implements Runnable {
        private Pipe pipe;

        public MessageOutput(Pipe pipe) {
            this.pipe = pipe;
        }

        @Override
        public void run() {
            try {
                String message = "hello world,libailugo";

                ByteBuffer buf = ByteBuffer.wrap(message.getBytes());
                Pipe.SinkChannel channel = pipe.sink();

                int count = channel.write(buf);
                channel.close();

                System.out.println("send message:" + message + ",length:"
                        + count);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static class MessageInput implements Runnable {
        private Pipe pipe;

        public MessageInput(Pipe pipe) {
            this.pipe = pipe;
        }

        @Override
        public void run() {
            try {
                Pipe.SourceChannel channel = pipe.source();
                ByteBuffer buf = ByteBuffer.allocate(10);

                StringBuilder sb = new StringBuilder();
                int count = channel.read(buf);
                while (count > 0) {
                    // 此处会导致错误
                    // sb.append(new String(buf.array()));
                    sb.append(new String(buf.array(), 0, count));
                    buf.clear();
                    count = channel.read(buf);
                }
                channel.close();
                System.out.println("recieve message:" + sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
