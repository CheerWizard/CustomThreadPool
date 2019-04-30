package utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class ThreadFactory {
    @NotNull
    @Contract("_, _ -> new")
    public static synchronized Thread createThread(Runnable runnable , String threadName) {
        return new Thread(runnable , threadName);
    }
}
