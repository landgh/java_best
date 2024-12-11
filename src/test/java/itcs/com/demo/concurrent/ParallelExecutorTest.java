package itcs.com.demo.concurrent;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.function.Supplier;
import java.util.function.Function;

public class ParallelExecutorTest {

    @Test
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
    public void testExecuteWithStringConcatenation() {
        ParallelExecutor executor = new ParallelExecutor();
        Supplier<String> task1 = () -> "Hello";
        Supplier<String> task2 = () -> " ";
        Supplier<String> task3 = () -> "World";

        Function<String[], String> concatConsolidator = (results) -> {
            StringBuilder sb = new StringBuilder();
            for (String result : results) {
                sb.append(result);
            }
            return sb.toString();
        };

        String result = executor.execute(concatConsolidator, task1, task2, task3);
        assertEquals("Hello World", result);
    }

    @Test
    public void testExecuteWithEmptySuppliers() {
        ParallelExecutor executor = new ParallelExecutor();

        Function<Object[], Integer> emptyConsolidator = (results) -> results.length;

        Integer result = executor.execute(emptyConsolidator);
        assertEquals(0, result);
    }
}