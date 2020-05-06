package com.learn.jdk.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/11 10:15
 */
@Slf4j
public class Client {

  public static void main(String[] args) throws IOException {
    Socket socket = new Socket();
    SocketAddress address = new InetSocketAddress("47.110.254.134", Const.PORT);
//        socket.bind(address);
    socket.connect(address);
//        for (int i = 0; i < 10; i++) {

    Random random = new Random();
    int num = random.nextInt(100);
    OutputStream outputStream = socket.getOutputStream();
    outputStream.write(("I'm Client " + num).getBytes());
    outputStream.flush();

    //收响应

    InputStream inputStream = socket.getInputStream();
    byte[] buffer = new byte[1024];
    inputStream.read(buffer);

    log.info("get response1:" + new String(buffer));

//        inputStream.close();
//        outputStream.close();

  }

//    }
}
