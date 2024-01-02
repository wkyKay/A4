package myrmi.server;

import myrmi.Remote;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class SkeletonReqHandler extends Thread {
    private Socket socket;
    private Remote obj;
    private int objectKey;

    public SkeletonReqHandler(Socket socket, Remote remoteObj, int objectKey) {
        this.socket = socket;
        this.obj = remoteObj;
        this.objectKey = objectKey;
    }
    private int determineCase(Object result) {
        if (result == null) {
            // Void method case
            return 1;
        } else if (result instanceof Exception) {
            // Exception thrown during method invocation
            return 0;
        } else {
            // Non-void method case
            return 2;
        }
    }
    @Override
    public void run() {
        int objectKey;
        String methodName;
        Class<?>[] argTypes;
        Object[] args;
        Object result;

        /*TODO: implement method here
         * You need to:
         * 1. handle requests from stub, receive invocation arguments, deserialization
         * 2. get result by calling the real object, and handle different cases (non-void method, void method, method throws exception, exception in invocation process)
         * Hint: you can use an int to represent the cases: -1 invocation error, 0 exception thrown, 1 void method, 2 non-void method
         *
         *  */
        try {
            // Handle requests from the stub
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            objectKey = in.readInt();
            methodName = (String) in.readObject();
            argTypes = (Class<?>[]) in.readObject();
            args = (Object[]) in.readObject();

            Method method = obj.getClass().getMethod(methodName, argTypes);
            result = method.invoke(obj, args);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(result);

            in.close();
            out.close();
            socket.close();

        } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
        }
    }
}
