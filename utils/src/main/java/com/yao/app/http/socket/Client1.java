package com.yao.app.http.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client1 {
    public static void main(String[] args) {
        try {
            Socket client = new Socket();
            // 8s连接超时
            client.connect(new InetSocketAddress("127.0.0.1",8088), 8000);
            client.setKeepAlive(true);
            // client.set
            /*PrintWriter bw =
                    new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream(),
                            Constants.DEFAULT_CHAR)));

            bw.println("hello world");
            bw.println("eof");
            bw.flush();*/
            OutputStreamWriter os = new OutputStreamWriter(client.getOutputStream(),
                    Constants.DEFAULT_CHAR);
            os.write("hello world\n");
            os.write("eof\n");
            os.flush();

            // 设置读取超时时间
            client.setSoTimeout(10*1000);  
            
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(client.getInputStream(), Constants.DEFAULT_CHAR));

            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                if (line.contains("eof")) {
                    break;
                }
                line = br.readLine();
            }
            
            br.close();
            //bw.close();
            os.close();
            
            

            System.out.println("from server:" + sb.toString());

            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
