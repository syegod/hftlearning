package io.sye.buffers;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

public class UnsafeHelper {

  public static Unsafe getUnsafe() {
    try {
      Field f = Unsafe.class.getDeclaredField("theUnsafe");
      f.setAccessible(true);
      return (Unsafe) f.get(null);
    } catch (Exception e) {
      throw new RuntimeException("Unable to get Unsafe", e);
    }
  }
}
