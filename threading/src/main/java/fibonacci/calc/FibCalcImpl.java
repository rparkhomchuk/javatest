package fibonacci.calc;

/**
 * A Fibonacci calculator eager instantiation Singleton implementation.
 */
public class FibCalcImpl implements FibCalc {

    private static FibCalc instance = new FibCalcImpl();

    private FibCalcImpl() {
    }

    public static FibCalc getInstance() {
        return instance;
    }

    @Override
    public long fib(int n) {
        long fibNumByIndex = calculateFibNumByIndex(n);
        System.out.println("Calculated num: " + fibNumByIndex);

        return fibNumByIndex;
    }

    private long calculateFibNumByIndex(int index) {
        if (index == 0) {
            return 0;
        } else if (index == 1) {
            return 1;
        } else {
            return calculateFibNumByIndex(index - 1) + calculateFibNumByIndex(index - 2);
        }
    }
}
