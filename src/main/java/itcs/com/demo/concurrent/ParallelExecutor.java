package itcs.com.demo.concurrent;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;

public class ParallelExecutor {

    /**
     * Executes a set of tasks in parallel and consolidates their results.
     *
     * @param <T>          the type of the task results
     * @param <R>          the type of the consolidated result
     * @param consolidator a function that consolidates the results of the tasks
     * @param tasks        an array of suppliers representing the tasks to be
     *                     executed
     * @return the consolidated result of the tasks
     * @throws ArrayStoreException if the runtime type of the specified array is not
     *                             a supertype of the runtime type of every element
     *                             in this list
     */
    @SuppressWarnings("unchecked")
    public final <T, R> R execute(Function<T[], R> consolidator, Supplier<T>... tasks) {
        Object[] tempResults = Arrays.stream(tasks)
                .map(Supplier::get)
                .toArray();

        if (tasks == null || tasks.length == 0) {
            return consolidator.apply((T[]) tempResults);
        }

        T[] results = (T[]) java.lang.reflect.Array.newInstance(tasks[0].get().getClass(), tempResults.length);
        System.arraycopy(tempResults, 0, results, 0, tempResults.length);
        return consolidator.apply(results);
    }
}
