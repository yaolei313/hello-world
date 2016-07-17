package com.yao.app.rmi.basic;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost",9999);
            Hello hello = (Hello) registry.lookup("Hello");
            String result = hello.sayHello();
            System.out.println(result);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

    }

}
