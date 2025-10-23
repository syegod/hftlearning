package io.sye.networking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;

public class SimpleServer {

  public static void main(String[] args) throws IOException {
    final var server = ServerSocketChannel.open();
    server.bind(new InetSocketAddress("192.168.0.11", 5000));

    while (true) {
      final var client = server.accept();

      final var buff = ByteBuffer.allocate(256);
      var bytesRead = client.read(buff);
      final var str = new StringBuilder();
      while (bytesRead != 0 && bytesRead != -1) {
        buff.flip();

        while (buff.hasRemaining()) {
          str.append(buff.getChar());
        }

        buff.clear();
        bytesRead = client.read(buff);
      }

      System.out.println(str);
      client.close();
    }
  }
}
