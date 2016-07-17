package com.yao.app.digest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;


public class DigestTest {

    public static void main(String[] args) {
        String key = "abc123";
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(key.getBytes());
            byte[] result = md.digest();
            output(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
                
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update("test".getBytes());
            byte[] result = md.digest(key.getBytes());
            output(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update("testabc123".getBytes());
            byte[] result = md.digest();
            output(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update("testabc123".getBytes());
            byte[] result = md.digest();
            
            for (int i = 1; i < 2; i++) {
                md.reset();
                result = md.digest(result);
            }
            
            output(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
    
    public static void output(byte[] s){
        Encoder encoder = Base64.getEncoder();
        System.out.println(encoder.encodeToString(s));
        
    }

}
