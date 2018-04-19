package com.yao.app.java.nio.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by yaolei02 on 2017/12/27.
 */
public class ZipTest {
    public static void main(String[] args) {
        try {
            ZipInputStream zipinputstream = new ZipInputStream(new FileInputStream("/data/atpco/Q340.PROD.S00TDTA.D171028.T2359.zip"));
            ZipEntry zipentry = zipinputstream.getNextEntry();
            while (zipentry != null) {
                String entryName = zipentry.getName();
                File newFile = new File(entryName);
                String directory = newFile.getParent();
                if (directory == null) {
                    if (newFile.isDirectory())
                        break;
                }
                RandomAccessFile rf = new RandomAccessFile(entryName, "r");
                String line;
                if ((line = rf.readLine()) != null) {
                    System.out.println(line);
                }
                rf.close();
                zipinputstream.closeEntry();
                zipentry = zipinputstream.getNextEntry();
            }
            zipinputstream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
