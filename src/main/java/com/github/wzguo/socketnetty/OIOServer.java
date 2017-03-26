package com.github.wzguo.socketnetty;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * IntelliJ IDEA 2016.3
 *
 * @author： admin
 * @date： 三月 04, 2017
 * @version: 1.0.0
 * @desc: socketNetty / com.github.wzguo.socketnetty
 */
public class OIOServer {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("the server started.");
            while (true) {
                final Socket socket = serverSocket.accept();
                System.out.println("the client connected.");
                service.execute(new Runnable() {
                    public void run() {
                        OIOServer.handlerClient(socket);
                    }
                });

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param socket
     */
    public static void handlerClient(Socket socket) {
        if (socket == null) {
            return;
        }
        System.out.println("the client connected: " + socket.getLocalPort());

        try {
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            while (true) {
                int read = inputStream.read(bytes);
                if (read == -1) {
                    break;
                }
                System.out.println(new String(bytes, 0, read));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
