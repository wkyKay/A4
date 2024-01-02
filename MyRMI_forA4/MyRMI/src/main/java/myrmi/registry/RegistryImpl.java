package myrmi.registry;

import myrmi.Remote;
import myrmi.exception.AlreadyBoundException;
import myrmi.exception.NotBoundException;
import myrmi.exception.RemoteException;
import myrmi.server.Skeleton;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;
public class RegistryImpl implements Registry {
    private final HashMap<String, Remote> bindings = new HashMap<>();

    /**
     * Construct a new RegistryImpl
     * and create a skeleton on given port
     **/
    public RegistryImpl(int port) throws RemoteException {
        Skeleton skeleton = new Skeleton(this, "0.0.0.0", port, 0);
        skeleton.start();
    }


    public Remote lookup(String name) throws RemoteException, NotBoundException {
        System.out.printf("RegistryImpl: lookup(%s)\n", name);
        //TODO: implement method here
        synchronized (bindings) {
            if (!bindings.containsKey(name)) {
                throw new NotBoundException(name + " is not bound in registry");
            }
            return bindings.get(name);
        }
    }

    public void bind(String name, Remote obj) throws RemoteException, AlreadyBoundException {
        System.out.printf("RegistryImpl: bind(%s)\n", name);
        //TODO: implement method here
        synchronized (bindings) {
            if (bindings.containsKey(name)) {
                throw new AlreadyBoundException(name + " is already bound in registry");
            }
            bindings.put(name, obj);
        }

    }

    public void unbind(String name) throws RemoteException, NotBoundException {
        System.out.printf("RegistryImpl: unbind(%s)\n", name);
        //TODO: implement method here
        synchronized (bindings) {
            if (!bindings.containsKey(name)) {
                throw new NotBoundException(name + " is not bound in registry");
            }
            bindings.remove(name);
        }

    }

    public void rebind(String name, Remote obj) throws RemoteException {
        System.out.printf("RegistryImpl: rebind(%s)\n", name);
        //TODO: implement method here
        synchronized (bindings) {
            bindings.put(name, obj);
        }
    }

    public String[] list() throws RemoteException {
        //TODO: implement method here
        synchronized (bindings) {
            return bindings.keySet().toArray(new String[0]);
        }
    }

    public static void main(String args[]) {
        final int regPort = (args.length >= 1) ? Integer.parseInt(args[0])
                : Registry.REGISTRY_PORT;
        RegistryImpl registry;
        try {
            registry = new RegistryImpl(regPort);
        } catch (RemoteException e) {
            System.exit(1);
        }
        System.out.printf("RMI Registry is listening on port %d\n", regPort);

    }
}
