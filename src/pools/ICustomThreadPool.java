package pools;

public interface ICustomThreadPool {
    void submitTask(Runnable task) throws InterruptedException;
}
