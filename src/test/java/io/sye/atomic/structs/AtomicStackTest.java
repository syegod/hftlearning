package io.sye.atomic.structs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;

class AtomicStackTest {

  @Test
  void testPushAndPop() {
    var stack = new AtomicStack<Long>();
    for (int i = 1; i <= 5; i++) {
      stack.push((long) (i * 10));
    }
    var i = stack.pop();
    assertEquals(50, i);
  }

  @Test
  void testPushWithContention() throws InterruptedException {
    var stack = new AtomicStack<Long>();
    var iters = 1_000_000;
    var threads = 10;
    var exec = Executors.newFixedThreadPool(threads);
    var latch = new CountDownLatch(threads);

    for (int i = 0; i < threads; i++) {
      exec.submit(() -> {
        for (long j = 1; j <= iters; j++) {
          stack.push(j);
        }
        latch.countDown();
      });
    }

    latch.await();
    exec.shutdown();

    assertEquals(iters * threads, stack.size());
    assertEquals(iters, stack.pop());
  }
}