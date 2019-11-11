package com.learn.jdk.socket;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.logging.Logger;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/11 10:15
 */
//@Slf4j
public class Server {
    static Logger log=Logger.getLogger(Server.class.getName());


    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket();
        SocketAddress address = new InetSocketAddress( Const.PORT);
        socket.bind(address);

        while (true) {
            Socket socket1 = socket.accept();
            InputStream inputStream = socket1.getInputStream();
            byte[] buffer = new byte[1024];
            inputStream.read(buffer);
            String recvMsg=new String(buffer);
            log.info("receive "+recvMsg);

            String sendMsg="response "+ recvMsg;
            OutputStream outputStream=socket1.getOutputStream();
            outputStream.write(sendMsg.getBytes());
            outputStream.flush();
            outputStream.close();

            log.info("waiting...");


        }

    }
}
