package cn.leon.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

//@BenchmarkMode({Mode.All})
//@OutputTimeUnit(TimeUnit.SECONDS)
public class Test {

  public static void main(String[] args) throws RunnerException {
      Options opt = new OptionsBuilder()
              .include(Test.class.getSimpleName())
              .forks(1)
              .build();
      new Runner(opt).run();
  }

    @Benchmark
    public void testPrint(){
    System.out.println("test");
    }
}
