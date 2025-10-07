package io.sye.buffers;

import sun.misc.Unsafe;

public class UnsafeLongArray implements AutoCloseable {

  private static final Unsafe U = UnsafeHelper.getUnsafe();
  private static final int LONG_SIZE = Long.BYTES;

  private final long address;
  private final int length;


  public UnsafeLongArray(int length) {
    this.length = length;
    this.address = U.allocateMemory(LONG_SIZE * (long) length);
  }

  public void set(int index, long value) {
    U.putLong(address + (long) index * LONG_SIZE, value);
  }

  public long get(int index) {
    return U.getLong(address + (long) index * LONG_SIZE);
  }

  public int length() {
    return length;
  }

  @Override
  public void close() {
    U.freeMemory(address);
  }
}
