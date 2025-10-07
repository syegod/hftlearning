package io.sye.benchmarks;

import io.sye.counters.AtomicCounter;
import io.sye.counters.Counter;
import io.sye.counters.SynchronizedCounter;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.GroupThreads;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(1)
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Group)
public class CounterBenchmark {

  private Counter atomicCounter;
  private Counter syncCounter;

  @Setup(Level.Iteration)
  public void setup() {
    atomicCounter = new AtomicCounter(0);
    syncCounter = new SynchronizedCounter(0);
  }

  @Group("atomic")
  @GroupThreads(100)
  @Benchmark
  public void testAtomicIncrement() {
    atomicCounter.increment();
    atomicCounter.get();
  }

  @Group("sync")
  @GroupThreads(100)
  @Benchmark
  public void testSyncIncrement() {
    syncCounter.increment();
    atomicCounter.get();
  }
}
