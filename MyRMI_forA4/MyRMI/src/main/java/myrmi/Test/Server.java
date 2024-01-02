package myrmi.Test;

import myrmi.Remote;
import myrmi.registry.LocateRegistry;
import myrmi.registry.Registry;
import myrmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry();
            Remote remoteObj = UnicastRemoteObject.exportObject(new MyRemoteImpl(),"127.0.0.2",8888 );
            registry.bind("myrmi.Test.MyRemoteInterface", remoteObj);
            System.out.println("Remote object registered.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
