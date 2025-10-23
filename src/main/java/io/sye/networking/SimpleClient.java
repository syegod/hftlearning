package io.sye.networking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class SimpleClient {

  public static void main(String[] args) throws IOException {
    final var socket = SocketChannel.open(new InetSocketAddress("192.168.0.11", 5000));

    socket.write(ByteBuffer.wrap("Hello, Server!".getBytes(StandardCharsets.UTF_8)));

    socket.close();
  }
}
