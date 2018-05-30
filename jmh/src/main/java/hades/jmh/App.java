package hades.jmh;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class App {
    @Setup
    public void setup() throws Exception {
        Thread.sleep(50);
    }

    @Benchmark
    public void test() throws Exception {
        Thread.sleep(900);
    }

    @TearDown
    public void tearDown() throws Exception {
        Thread.sleep(50);
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(App.class.getSimpleName()).warmupBatchSize(2)// 预热
                .measurementIterations(10)// run
                .forks(1).build();
        new Runner(options).run();
    }
}
