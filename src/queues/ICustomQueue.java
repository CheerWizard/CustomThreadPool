package queues;

public interface ICustomQueue<Task> {
    void enqueue(Task task) throws InterruptedException;
    Task dequeue() throws InterruptedException;
}
