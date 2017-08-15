package ymartin.traffic;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;

public class FakeScheduledExecutorService implements ScheduledExecutorService {
    private Map<Duration, Queue<Runnable>> futureCommands = new TreeMap<>(Comparator.naturalOrder());

    public void executeScheduled(Duration duration) {
        futureCommands.entrySet()
                .stream()
                .filter(durationQueueEntry -> durationQueueEntry.getKey().compareTo(duration) <= 0)
                .forEach(durationQueueEntry -> {
                    Queue<Runnable> commands = durationQueueEntry.getValue();
                    for (Runnable command = commands.poll(); command != null; command = commands.poll()) {
                        command.run();
                    }
                });
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        Duration duration = Duration.ofSeconds(unit.toSeconds(delay));
        futureCommands.putIfAbsent(duration, new LinkedList<>());
        futureCommands.get(duration).add(command);
        return null;
    }

    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        return null;
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return null;
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return null;
    }

    @Override
    public void shutdown() {

    }

    @Override
    public List<Runnable> shutdownNow() {
        return null;
    }

    @Override
    public boolean isShutdown() {
        return false;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return null;
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return null;
    }

    @Override
    public Future<?> submit(Runnable task) {
        return null;
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return null;
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return null;
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    @Override
    public void execute(Runnable command) {

    }
}
