package fibonacci.tester;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * Performance tester implememntation.
 */
public class PerformanceTesterImpl implements PerformanceTester {

    @Override
    public PerformanceTestResult runPerformanceTest(Runnable task, int executionCount, int threadPoolSize) throws InterruptedException {
        ArrayList<Future<Long>> futures = new ArrayList<>(executionCount);
        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);

        //submit task executionCount times
        for (int i = 0; i < executionCount; i++) {
            futures.add(executor.submit(new RunnableTask(task)));
        }
        //retrieve the results
        long total = 0L;
        long min = Long.MAX_VALUE;
        long max = 0L;

        for (Future<Long> future : futures) {
            try {
                long result = future.get();
                System.out.println("Task execution time: " + result);
                total += result;

                if (min > result) {
                    min = result;
                } else if (max < result) {
                    max = result;
                }
            } catch (ExecutionException e) {
                System.out.println("Exception while performing the test: " + e.getMessage());
            }
        }
        executor.shutdown();

        return new PerformanceTestResult(total, min, max);
    }

    class RunnableTask implements Callable<Long> {
        private Runnable runnable;

        public RunnableTask(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public Long call() {
            long start = System.currentTimeMillis();
            runnable.run();
            long end = System.currentTimeMillis();

            return end - start;
        }
    }

}
