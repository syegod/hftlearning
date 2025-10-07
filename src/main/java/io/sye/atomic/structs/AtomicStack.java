package io.sye.atomic.structs;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicStack<T> {

  private final AtomicReference<Node<T>> head = new AtomicReference<>();

  private final AtomicInteger size = new AtomicInteger(0);

  public void push(T item) {
    var newHead = new Node<>(item);
    Node<T> oldHead;
    do {
      oldHead = head.get();
      newHead.next = oldHead;
    } while (!head.compareAndSet(oldHead, newHead));
    size.incrementAndGet();
  }

  public T pop() {
    Node<T> newHead;
    Node<T> oldHead;
    do {
      oldHead = head.get();
      if (oldHead == null) {
        return null;
      }
      newHead = oldHead.next;
    } while (!head.compareAndSet(oldHead, newHead));
    size.decrementAndGet();
    return oldHead.value;
  }

  public int size() {
    return size.get();
  }

  private static class Node<T> {

    private final T value;
    private Node<T> next;

    public Node(T value) {
      this.value = value;
    }
  }
}
