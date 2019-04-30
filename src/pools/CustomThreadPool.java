package pools;

import executors.CustomTaskExecutor;
import org.jetbrains.annotations.NotNull;
import queues.CustomQueue;
import queues.ICustomQueue;
import executors.TaskExecutorFactory;
import utils.ThreadFactory;

import java.util.List;

public class CustomThreadPool implements ICustomThreadPool{

    private ICustomQueue<Runnable> queue;
    private List<String> threadNames;

    public CustomThreadPool(int queueSize, List<CustomTaskExecutor> customTaskList) {
        queue = new CustomQueue<>(queueSize);
        initThreads(customTaskList);
    }

    public CustomThreadPool(int queueSize , int threadSize) {
        queue = new CustomQueue<>(queueSize);
        initThreads(threadSize);
    }

    public ICustomQueue<Runnable> getQueue() {
        return queue;
    }

    public void setQueue(ICustomQueue<Runnable> queue) {
        this.queue = queue;
    }

    public List<String> getThreadNames() {
        return threadNames;
    }

    public void setThreadNames(List<String> threadNames) {
        this.threadNames = threadNames;
    }

    private synchronized void initThreads(@NotNull List<CustomTaskExecutor> customTaskExecutors) {
        for (int i = 0; i < customTaskExecutors.size(); i++)
            ThreadFactory
                    .createThread(customTaskExecutors.get(i) , "Thread - " + i)
                    .start();
    }

    private synchronized void initThreads(int threadSize) {
        for (int i = 0; i < threadSize; i++)
            ThreadFactory
                    .createThread(TaskExecutorFactory.createCustomTaskExecutor((CustomQueue<Runnable>) queue) , "Thread - " + i)
                    .start();
    }

    @Override
    public void submitTask(Runnable task) throws InterruptedException {
        queue.enqueue(task);
    }
}