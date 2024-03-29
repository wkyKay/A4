package myrmi.server;

import myrmi.Remote;
import myrmi.exception.RemoteException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


public class UnicastRemoteObject implements Remote, java.io.Serializable {
    int port;
    static int key = 1;

    protected UnicastRemoteObject() throws RemoteException {
        this(0);
    }

    protected UnicastRemoteObject(int port) throws RemoteException {
        this.port = port;
        exportObject(this, port);
    }

    public static Remote exportObject(Remote obj) throws RemoteException {
        return exportObject(obj, 0);
    }

    public static Remote exportObject(Remote obj, int port) throws RemoteException {
        return exportObject(obj, "127.0.0.1", port);
    }

    /**
     * 1. create a skeleton of the given object ``obj'' and bind with the address ``host:port''
     * 2. return a stub of the object ( Util.createStub() )
     **/
    public static Remote exportObject(Remote obj, String host, int port) {
        //TODO: finish here
        int newObjectKey = key++;
        String interfaceName = obj.getClass().getInterfaces()[0].getName();
        RemoteObjectRef ref = new RemoteObjectRef(host, port, newObjectKey, interfaceName);
        Skeleton skeleton = new Skeleton(obj, ref);
        skeleton.start();
        return Util.createStub(ref);

    }
}
