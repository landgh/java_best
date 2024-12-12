package itcs.com.demo.concurrent;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

public class ParallelExecutorTest {

    @Test
    @SuppressWarnings("unchecked")
    public void testExecuteWithIntegerSum() {
        ParallelExecutor executor = new ParallelExecutor();
        Supplier<Integer> task1 = () -> 1;
        Supplier<Integer> task2 = () -> 2;
        Supplier<Integer> task3 = () -> 3;

        Function<Integer[], Integer> sumConsolidator = (results) -> {
            int sum = 0;
            for (int result : results) {
                sum += result;
            }
            return sum;
        };

        Integer result = executor.execute(sumConsolidator, task1, task2, task3);
        assertEquals(6, result);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testExecuteWithEmptySuppliers_Then_Exception() {
        ParallelExecutor executor = new ParallelExecutor();

        Function<Object[], Integer> emptyConsolidator = (results) -> results.length;
        assertThrows(IllegalArgumentException.class, () -> {
            executor.execute(emptyConsolidator);
        });
    }

    // Add more test cases to improve coverage if necessary
}