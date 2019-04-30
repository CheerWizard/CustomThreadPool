package queues;

import java.util.LinkedList;
import java.util.Queue;

public class CustomQueue<T extends Runnable> implements ICustomQueue<T> {

    private Queue<T> queue = new LinkedList<>();
    private int EMPTY = 0;
    private int MAX_TASK_IN_QUEUE;

    public CustomQueue(int size){
        this.MAX_TASK_IN_QUEUE = size;
    }

    public Queue<T> getQueue() {
        return queue;
    }

    public void setQueue(Queue<T> queue) {
        this.queue = queue;
    }

    public int getEMPTY() {
        return EMPTY;
    }

    public void setEMPTY(int EMPTY) {
        this.EMPTY = EMPTY;
    }

    public int getMAX_TASK_IN_QUEUE() {
        return MAX_TASK_IN_QUEUE;
    }

    public void setMAX_TASK_IN_QUEUE(int MAX_TASK_IN_QUEUE) {
        this.MAX_TASK_IN_QUEUE = MAX_TASK_IN_QUEUE;
    }

    @Override
    public synchronized void enqueue(T task) throws InterruptedException  {
        while(this.queue.size() == this.MAX_TASK_IN_QUEUE) wait();
        if(this.queue.size() == EMPTY) notifyAll();
        this.queue.offer(task);
    }

    @Override
    public synchronized T dequeue() throws InterruptedException{
        while(this.queue.size() == EMPTY) wait();
        if(this.queue.size() == this.MAX_TASK_IN_QUEUE) notifyAll();
        return this.queue.poll();
    }
}