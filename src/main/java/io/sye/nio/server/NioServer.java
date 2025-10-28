package io.sye.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class NioServer {

  private static final int PORT = 8080;
  private static final Set<SocketChannel> connected = new HashSet<>();

  public static void main(String[] args) throws IOException {
    final var selector = Selector.open();
    final var server = ServerSocketChannel.open();
    server.configureBlocking(false);
    server.bind(new InetSocketAddress(PORT));
    server.register(selector, SelectionKey.OP_ACCEPT);

    System.out.println("Server started on port: " + PORT);

    final var buffer = ByteBuffer.allocate(1024);

    while (true) {
      selector.select();

      final var keys = selector.selectedKeys().iterator();
      while (keys.hasNext()) {
        final var key = keys.next();
        keys.remove();

        try {
          if (key.isAcceptable()) {
            acceptClient(server, selector);
          }

          if (key.isReadable()) {
            readFromClient(key, buffer);
          }
        } catch (IOException ex) {
          key.cancel();
          closeChannel(key);
        }
      }
    }
  }

  public static void acceptClient(ServerSocketChannel server, Selector selector)
      throws IOException {
    final var client = server.accept();
    client.configureBlocking(false);
    client.register(selector, SelectionKey.OP_READ);
    System.out.println("Connected: " + getAddress(client));
    connected.add(client);
  }

  public static void readFromClient(SelectionKey key, ByteBuffer buffer) throws IOException {
    final var client = (SocketChannel) key.channel();
    buffer.clear();
    int bytesRead = client.read(buffer);
    if (bytesRead == -1) {
      closeChannel(key);
    }
    buffer.flip();
    final var message = new String(buffer.array(), StandardCharsets.UTF_8);
    System.out.println(
        getAddress(client) + ": " + message);
    broadcast(message, client.getRemoteAddress());
  }

  private static void broadcast(String message, SocketAddress sender) throws IOException {
    final var buffer = ByteBuffer.wrap(
        (sender.toString().substring(1) + ": " + message).getBytes(StandardCharsets.UTF_8));
    for (var c : connected) {
      if (!sender.equals(c.getRemoteAddress())) {
        buffer.rewind();
        c.write(buffer);
      }
    }
  }

  private static void closeChannel(SelectionKey key) {
    try {
      if (key.channel() != null) {
        key.channel().close();
      }
    } catch (IOException ignored) {
    }
  }

  private static String getAddress(SocketChannel channel) throws IOException {
    return channel.getRemoteAddress().toString().substring(1);
  }
}
