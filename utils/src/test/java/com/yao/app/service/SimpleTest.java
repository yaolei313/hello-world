package com.yao.app.service;

import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * @author yaolei03 <yaolei03@kuaishou.com> Created on 2022-03-25
 */
public class SimpleTest {

    @Test
    public void test1() {
        String s = "\\x10\\xE7\\x10\\xEC\\xBE\\xB5\\xC9\\x9AFW\\x88\\xD2\\x1C\\x054\\x09";

        byte[] a = encrypt("+8618511073152");
        System.out.println(a);
        System.out.println(Hex.encodeHexString(a));
    }

    public static byte[] encrypt(String data) {
        if (StringUtils.isBlank(data)) {
            return new byte[0];  //写表保证不报错（长度0）
        }
        return encrypt("CA6129A81E832118".getBytes(StandardCharsets.UTF_8), "B31E74D6C05F1CFA", data.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] encrypt(byte[] key, String initVector, byte[] bytes) {
        return transform(key, initVector, bytes, 1);
    }

    private static byte[] transform(byte[] key, String initVector, byte[] bytes, int mode) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(mode, keySpec, ivSpec);
            return cipher.doFinal(bytes);
        } catch (Exception var7) {
            if (var7 instanceof RuntimeException) {
                throw (RuntimeException)var7;
            } else {
                throw new RuntimeException(var7);
            }
        }
    }
}
