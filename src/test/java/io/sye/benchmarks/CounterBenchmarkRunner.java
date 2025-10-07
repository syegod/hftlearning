package io.sye.benchmarks;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class CounterBenchmarkRunner {

  public static void main(String[] args) throws RunnerException {
    final var opt = new OptionsBuilder()
        .include(CounterBenchmark.class.getSimpleName())
        .build();

    new Runner(opt).run();
  }
}
