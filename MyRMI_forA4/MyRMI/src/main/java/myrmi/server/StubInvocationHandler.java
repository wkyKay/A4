package myrmi.server;

import com.sun.corba.se.spi.ior.ObjectKey;
import myrmi.exception.RemoteException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;

public class StubInvocationHandler implements InvocationHandler, Serializable {
    private String host;
    private int port;
    private int objectKey;

    public StubInvocationHandler(String host, int port, int objectKey) {
        this.host = host;
        this.port = port;
        this.objectKey = objectKey;
        System.out.printf("Stub created to %s:%d, object key = %d\n", host, port, objectKey);
    }

    public StubInvocationHandler(RemoteObjectRef ref) {
        this(ref.getHost(), ref.getPort(), ref.getObjectKey());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws RemoteException, IOException, ClassNotFoundException, Throwable {
        /*TODO: Implement Stub Proxy Invocation Handler Logic
         * Instructions:
         *  You need to:
         * 1. Connect to the remote skeleton and send the method along with its arguments.
         * 2. Retrieve the result from the remote skeleton and transparently return it to the caller.
         * */
        try {
            System.out.println("Stub Invoke " + method.getName());
            Socket socket = new Socket(host, port);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            out.writeInt(objectKey);
            out.writeObject(method.getName());
            out.writeObject(method.getParameterTypes());
            out.writeObject(args);

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Retrieve the result from the remote skeleton and transparently return it to the caller
            Object result = in.readObject();

            // Close streams and socket
            in.close();
            out.close();
            socket.close();

            return result;

        } catch (IOException e) {
            throw new RemoteException();
        }

    }

}
