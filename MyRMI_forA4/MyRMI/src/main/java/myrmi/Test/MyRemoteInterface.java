package myrmi.Test;

import myrmi.Remote;

public interface MyRemoteInterface extends Remote {

    int port = 8888;
    String host = "127.0.0.2";
    String sayHello();

    int calculate(int a, int b);
}