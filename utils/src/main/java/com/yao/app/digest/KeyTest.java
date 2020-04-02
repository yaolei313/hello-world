package com.yao.app.digest;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 * Created by yaolei02 on 2017/4/1.
 */
public class KeyTest {
    public static void main(String[] args) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128, new SecureRandom("ship".getBytes()));
            SecretKey key = keyGenerator.generateKey();
            byte[] bytes = key.getEncoded();
            System.out.println(bytes2HexStr(bytes));

            System.out.println("---------");
            encrypt();

            /*
            String str = "E4445D82AFAEF51FC14910E7A93E412F";
            byte[] tt = hexStr2bytes(str);
            System.out.println(bytes2BinaryStr(tt));
            System.out.println(bytes2HexStr(tt));
            */

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void encrypt(){
        try {
            byte[] bytes = hexStr2bytes("E4445D82AFAEF51FC14910E7A93E412F");
            SecretKeySpec secretKeySpec = new SecretKeySpec(bytes, "AES");// 转换为AES专用密钥
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = "120106199309148719".getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);// 初始化为加密模式的密码器
            byte[] result = cipher.doFinal(byteContent);// 加密
            System.out.println(bytes2HexStr(result));
            System.out.println(new String(result, "utf-8"));
            Base64 base64 = new Base64();
            System.out.println(base64.encodeToString(result));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void decrypt(){

    }

    public static String bytes2BinaryStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String binary = Integer.toBinaryString(buf[i] & 0xFF);
            sb.append(binary);
        }
        return sb.toString();
    }

    public static String bytes2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            // toHexString() 方法的参数是int(32位)类型，如果输入一个byte(8位)类型的数字，这个
            // 方法会把这个数字的高24为也看作有效位，这就必然导致错误，使用& 0xFF操作，可以把高24位置0以避免这样错误。
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] hexStr2bytes(String str) {
        if (str.length() % 2 == 1) {
            throw new IllegalArgumentException("参数非法,长度不为偶数");
        }
        char[] chars = str.toCharArray();
        if (chars.length != str.length()) {
            throw new IllegalArgumentException("包含非法字符，只允许[0-9a-zA-Z]");
        }
        byte[] result = new byte[chars.length / 2];
        for (int i = 0; i < chars.length; i = i + 2) {
            int higher = Character.getNumericValue(chars[i]) << 4;
            int lower = Character.getNumericValue(chars[i + 1]);
            result[i / 2] = (byte) (higher | lower);
        }
        return result;
    }


}
