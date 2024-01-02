package myrmi.Test;

// MyRemoteImpl.java

import myrmi.server.Skeleton;

public class MyRemoteImpl implements MyRemoteInterface {
   public MyRemoteImpl(){
   }

    @Override
    public String sayHello() {
       System.out.println("MyRemoteImpl: sayHello");
       return "Hello, RMI World!";
    }

    @Override
    public int calculate(int a, int b) {
        System.out.println("MyRemoteImpl: calculate");
        return a+b;
    }
}
