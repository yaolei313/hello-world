package com.yao.app.rmi.basic;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements Hello {

    @Override
    public String sayHello() {
        return "Hello world!";
    }

    public static void main(String args[]) {

        try {
            Server obj = new Server();
            Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0);
            
            LocateRegistry.createRegistry(9999);
            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry("localhost",9999);
            registry.bind("Hello", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
