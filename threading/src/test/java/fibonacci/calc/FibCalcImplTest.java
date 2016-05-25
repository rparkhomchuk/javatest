package fibonacci.calc;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Roma on 25.05.2016.
 */
public class FibCalcImplTest {

    @Test
    public void testFib() throws Exception {
        FibCalc fibCalc = FibCalcImpl.getInstance();
        assertEquals(1, fibCalc.fib(1));
        assertEquals(1, fibCalc.fib(2));
        assertEquals(2, fibCalc.fib(3));
        assertEquals(3, fibCalc.fib(4));
        assertEquals(5, fibCalc.fib(5));
        assertEquals(8, fibCalc.fib(6));
        assertEquals(13, fibCalc.fib(7));
        assertEquals(21, fibCalc.fib(8));
        assertEquals(34, fibCalc.fib(9));
        assertEquals(55, fibCalc.fib(10));
    }
}