package com.yao.app.jmx.security.simple.server;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;

public class Registry {

    public static void main(String[] args) {
        // RMI Registry
        try {
            LocateRegistry.createRegistry(9999);
            System.out.println("input any charater to stop");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }    

    }

}
