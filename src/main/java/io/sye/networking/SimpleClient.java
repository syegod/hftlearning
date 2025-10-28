package io.sye.networking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SimpleClient {

  public static void main(String[] args) throws IOException {
    final var channel = SocketChannel.open();
    channel.connect(new InetSocketAddress("localhost", 8080));
    channel.configureBlocking(false);

    ByteBuffer buffer = ByteBuffer.allocate(1024);
    Scanner scanner = new Scanner(System.in);

    System.out.println("Client started. Print 'exit' to stop");

    while (true) {
      System.out.print("> ");
      String input = scanner.nextLine();

      if ("exit".equalsIgnoreCase(input)) {
        break;
      }

      channel.write(ByteBuffer.wrap((input).getBytes()));

      buffer.clear();
      int read = channel.read(buffer);
      if (read > 0) {
        buffer.flip();
        System.out.println(new String(buffer.array(), 0, buffer.limit(),
            StandardCharsets.UTF_8).trim());
      } else if (read == -1) {
        System.out.println("Server closed connection");
        break;
      }
    }

    channel.close();
  }
}
