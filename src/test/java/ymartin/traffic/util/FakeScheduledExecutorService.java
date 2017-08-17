package ymartin.traffic.util;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class FakeScheduledExecutorService implements ScheduledExecutorService {
    private Map<Duration, Queue<Runnable>> futureCommands = new HashMap<>();
    private boolean isShutdown = false;

    public void executeScheduled(Duration... durations) {
        Arrays.stream(durations)
                .map(duration -> futureCommands.getOrDefault(duration, new LinkedList<>()).poll())
                .filter(Objects::nonNull)
                .forEach(Runnable::run);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public ScheduledFuture<?> schedule(Runnable runnable, long delay, TimeUnit unit) {
        if (!isShutdown()) {
            Duration duration = Duration.ofMillis(unit.toMillis(delay));
            futureCommands.putIfAbsent(duration, new LinkedList<>());
            futureCommands.get(duration).add(runnable);
        }
        return null;
    }

    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void shutdown() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Runnable> shutdownNow() {
        isShutdown = true;
        List<Runnable> commands = futureCommands.values().stream().map(Queue::poll).collect(Collectors.toList());
        futureCommands.clear();
        return commands;
    }

    @Override
    public boolean isShutdown() {
        return isShutdown;
    }

    @Override
    public boolean isTerminated() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {

        throw new UnsupportedOperationException();
    }

    @Override
    public Future<?> submit(Runnable task) {

        throw new UnsupportedOperationException();
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void execute(Runnable command) {
        throw new UnsupportedOperationException();
    }
}
