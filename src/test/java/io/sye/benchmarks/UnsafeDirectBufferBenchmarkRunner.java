package io.sye.benchmarks;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class UnsafeDirectBufferBenchmarkRunner {

  public static void main(String[] args) throws RunnerException {
    var opts = new OptionsBuilder().include(UnsafeDirectBufferBenchmark.class.getSimpleName())
        .build();

    new Runner(opts).run();
  }

}
