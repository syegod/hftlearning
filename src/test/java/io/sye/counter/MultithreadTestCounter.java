package io.sye.counter;


import static org.junit.jupiter.api.Assertions.assertEquals;

import io.sye.counters.AtomicCounter;
import io.sye.counters.Counter;
import io.sye.counters.SynchronizedCounter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MultithreadTestCounter {

  private static final int THREADS = 100;
  private static final int INCREMENTS_PER_THREAD = 1000;

  private CountDownLatch latch;
  private ExecutorService executor;

  @BeforeEach
  void beforeEach() {
    latch = new CountDownLatch(THREADS);
    executor = Executors.newFixedThreadPool(THREADS);
  }

  @Test
  void testMultithreadedIncrementAtomic() throws InterruptedException {
    final var counter = new AtomicCounter(0);

    multithreadCounter(counter);

    assertEquals(THREADS * INCREMENTS_PER_THREAD, counter.get());
  }

  @Test
  void testMultithreadedIncrementSync() throws InterruptedException {
    final var counter = new SynchronizedCounter(0);

    multithreadCounter(counter);

    assertEquals(THREADS * INCREMENTS_PER_THREAD, counter.get());
  }

  private void multithreadCounter(Counter counter) throws InterruptedException {
    for (int i = 0; i < THREADS; i++) {
      executor.submit(() -> {
        for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
          counter.increment();
        }
        latch.countDown();
      });
    }

    latch.await();
    executor.shutdown();
  }
}
