package io.sye.unsafe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.sye.buffers.UnsafeLongArray;
import org.junit.jupiter.api.Test;

public class UnsafeLongArrayTest {

  @Test
  void testSetAndGet() {
    var l = 10;
    try (var arr = new UnsafeLongArray(l)) {
      for (int i = 0; i < l; i++) {
        arr.set(i, i * 5);
      }

      assertEquals(25, arr.get(5));
    }
  }

  @Test
  void testLength() {
    var l = 5;
    try (var arr = new UnsafeLongArray(l)) {
      for (int i = 0; i < l; i++) {
        arr.set(i, i * 5);
      }

      assertEquals(5, arr.length());
    }
  }

}
