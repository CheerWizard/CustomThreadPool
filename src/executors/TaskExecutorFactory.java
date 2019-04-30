package executors;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import queues.CustomQueue;

public final class TaskExecutorFactory {
    @NotNull
    @Contract("_ -> new")
    public static synchronized CustomTaskExecutor createCustomTaskExecutor(CustomQueue<Runnable> customQueue) {
        return new CustomTaskExecutor(customQueue);
    }
}
