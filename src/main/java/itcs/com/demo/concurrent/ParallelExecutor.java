package itcs.com.demo.concurrent;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
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
        if (tasks.length == 0) {
            throw new IllegalArgumentException("Tasks cannot be null");
        }

        CompletableFuture<T>[] futures = Arrays.stream(tasks)
                .map(task -> CompletableFuture.supplyAsync(task))
                .toArray(CompletableFuture[]::new);

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures);

        CompletableFuture<T[]> allResults = allOf.thenApply(v -> Arrays.stream(futures)
                .map(CompletableFuture::join)
                .toArray(size -> (T[]) java.lang.reflect.Array.newInstance(tasks[0].get().getClass(), size)));

        return consolidator.apply(allResults.join());
    }
}
