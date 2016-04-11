package com.yao.app.java.jvm;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class SizeCalculator {
    public static int calcSize(java.io.Serializable o) {
        int ret = 0;
        class DumbOutputStream extends OutputStream {
            int count = 0;
            
            private FileOutputStream outputStream;
            
            public DumbOutputStream(FileOutputStream outputStream) {
                this.outputStream = outputStream;
            }
            
            public void write(int b) throws IOException {
                outputStream.write(b);
                count++; // 只计数，不产生字节转移
            }
        }
        
        ObjectOutputStream os = null;
        try {
            DumbOutputStream buf = new DumbOutputStream(new FileOutputStream("./temp.txt"));
            os = new ObjectOutputStream(buf);
            os.writeObject(o);
            ret = buf.count;
        } catch (IOException e) {
            // No need handle this exception
            e.printStackTrace();
            ret = -1;
        } finally {
            try {
                os.close();
            } catch (Exception e) {
            }
        }
        return ret;
    }

    public static void main(String[] args) throws Exception{
        Student st = new Student();
        st.setId(1);
        st.setCode("111");
        //System.out.println(calcSize(st));
        System.out.println(SizeOfObject.fullSizeOf(st)); // 96byte
        System.out.println(SizeOfObject.sizeOf(st));  //32byte
        System.out.println(SizeOfObject.sizeOf(new Object()));
    }
}
