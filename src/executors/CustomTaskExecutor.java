package executors;

import queues.CustomQueue;

public class CustomTaskExecutor implements Runnable {

    private CustomQueue<Runnable> queue;

    public CustomTaskExecutor(CustomQueue<Runnable> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            //get task and name of each thread
            final String name = Thread.currentThread().getName();
            final Runnable task = queue.dequeue();
            //track process in console
            System.out.println("Task Started by Thread : " + name);
            //run appropriate task
            task.run();
            //track process in console
            System.out.println("Task Finished by Thread : " + name);
        } catch (InterruptedException e) {
            System.exit(0);
        }
    }
}
