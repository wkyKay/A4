package myrmi.Test;

import myrmi.exception.NotBoundException;
import myrmi.exception.RemoteException;
import myrmi.registry.LocateRegistry;
import myrmi.registry.Registry;
import myrmi.server.SkeletonReqHandler;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Client extends Thread{
    int id;
    public Client(int i){
        id = i;
    }
    @Override
    public void run() {
        try {
            Random random = new Random();
            int a = random.nextInt(20);
            int b = random.nextInt(20);
            // Create a server socket to listen for incoming connections
            Registry registry = LocateRegistry.getRegistry();
            MyRemoteInterface remoteObj = (MyRemoteInterface) registry.lookup("myrmi.Test.MyRemoteInterface");
            String result = remoteObj.sayHello();
            System.out.println("Remote method result of " +id + ": "+ result);
            System.out.println("Remote method result of " + id + ": " + a+ "+" + b + " = " +remoteObj.calculate(a, b));

        } catch (NotBoundException | RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[]args){
        List<Client> clients = new ArrayList<>();
        for(int i = 0; i < 4; i++){
           clients.add(new Client(i));
        }
        for (int i = 0; i < 4; i++){
            clients.get(i).start();
        }
    }
}
