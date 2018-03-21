package com.example.redent0r.ethernal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by redent0r
 */

public class GreetingServer extends Thread {

    private ServerSocket serverSocket;
    MainActivity mainActivity;
    static final int socketServerPORT = 7575;

    public GreetingServer(MainActivity mainActivity) throws IOException {
        this.mainActivity = mainActivity;
        serverSocket = new ServerSocket(socketServerPORT);
        serverSocket.setSoTimeout(10000);
    }

    public void run() {
        while(true) {
            try {
                Socket server = serverSocket.accept();

                DataInputStream in = new DataInputStream(server.getInputStream());

                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress()
                        + "\nGoodbye!");

                server.close();

            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}