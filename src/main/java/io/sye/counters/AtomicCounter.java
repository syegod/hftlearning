package io.sye.counters;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicCounter implements Counter {

  private final AtomicLong counter;

  public AtomicCounter(long l) {
    this.counter = new AtomicLong(l);
  }

  public void increment() {
    counter.incrementAndGet();
  }

  public long get() {
    return counter.get();
  }
}
