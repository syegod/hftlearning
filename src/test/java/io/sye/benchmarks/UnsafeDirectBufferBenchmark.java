package io.sye.benchmarks;

import io.sye.buffers.UnsafeLongArray;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;

@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class UnsafeDirectBufferBenchmark {

  private static final int ARR_LENGTH = 10_000_000;

  private UnsafeLongArray unsafe;
  private ByteBuffer buff;
  private long[] arr;

  @Setup(Level.Trial)
  public void setup() {
    unsafe = new UnsafeLongArray(ARR_LENGTH);
    arr = new long[ARR_LENGTH];
    buff = ByteBuffer.allocateDirect(ARR_LENGTH * Long.BYTES).order(ByteOrder.nativeOrder());
  }

  @TearDown(Level.Trial)
  public void tearDown() {
    unsafe.close();
    buff = null;
    arr = null;
  }

  @Benchmark
  public void testUnsafeSet() {
    for (int i = 0; i < ARR_LENGTH; i++) {
      unsafe.set(i, i);
    }
  }

  @Benchmark
  public void testDirectBufferSet() {
    for (int i = 0; i < ARR_LENGTH; i++) {
      buff.putLong(i * Long.BYTES, i);
    }
  }

  @Benchmark
  public void testArrayInsert() {
    for (int i = 0; i < ARR_LENGTH; i++) {
      arr[i] = i;
    }
  }
}
