package myrmi.server;

import myrmi.Remote;
import myrmi.Test.MyRemoteInterface;
import myrmi.registry.Registry;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Util {


    public static Remote createStub(RemoteObjectRef ref) {
        //TODO: finish here, instantiate an StubInvocationHandler for ref and then return a stub
        StubInvocationHandler invocationHandler = new StubInvocationHandler("server", ref.getPort(), ref.getObjectKey());
        try {
            return (Remote) Proxy.newProxyInstance(Class.forName(ref.getInterfaceName()).getClassLoader(), new Class[]{Class.forName(ref.getInterfaceName())}, invocationHandler);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
