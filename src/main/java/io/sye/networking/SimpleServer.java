package io.sye.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;

public class SimpleServer {

  public static void main(String[] args) throws IOException {
    final var server = new ServerSocket(8000);

    while (true) {
      final var client = server.accept();
      System.out.println(
          "Established connection with: " + client.getInetAddress().getHostAddress());
      final var in = client.getInputStream();
      final var out = client.getOutputStream();

      int rcvd;
      final var bytes = new byte[64];
      while ((rcvd = in.read(bytes)) != -1) {
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
        out.write(bytes);
      }
      client.close();
    }
  }
}
