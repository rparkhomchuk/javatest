package fibonacci;

import fibonacci.calc.FibCalcImpl;
import fibonacci.tester.PerformanceTestResult;
import fibonacci.tester.PerformanceTesterImpl;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Fibonacci number index: ");
        String fibIndex = scanner.nextLine();
        System.out.println("Calculation count: ");
        String calcCount = scanner.nextLine();
        System.out.println("Thread pool size: ");
        String poolSize = scanner.nextLine();

        PerformanceTesterImpl tester = new PerformanceTesterImpl();

        try {
            PerformanceTestResult testResult = tester.runPerformanceTest(new Runnable() {
                @Override
                public void run() {
                    FibCalcImpl.getInstance().fib(Integer.parseInt(fibIndex));
                }
            }, Integer.parseInt(calcCount), Integer.parseInt(poolSize));

            System.out.println("Test result: " + testResult.toString());
        } catch (Exception e) {
            System.out.println("Got an exception: " + e.getMessage());
        }
    }
}
