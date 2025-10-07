package io.sye.counters;

public class SynchronizedCounter implements Counter {

  private long counter;

  public SynchronizedCounter(long counter) {
    this.counter = counter;
  }

  public synchronized void increment() {
    counter++;
  }

  public synchronized long get() {
    return counter;
  }
}
