package ru.naumen.collection.task4;

import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * Класс управления расчётами
 */
public class ConcurrentCalculationManager<T> {
    /**
     * Так как по условию необходимо выводить задачи в их порядке, выбор пал на очереди.
     * ConcurrentLinkedQueue работает на связных списках, поэтому вставка и удаление будет
     * работать со сложностью O(1).
     */
    private final Queue<Future<T>> blockingQueue = new ConcurrentLinkedQueue<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final AtomicInteger tasksAdded = new AtomicInteger(0);

    /**
     * Добавить задачу на параллельное вычисление
     */
    public void addTask(Supplier<T> task) throws InterruptedException {
        /**
         * Сложность определяется самой задачей, а так как в самой задаче мы просто получаем
         * один объект из функционального интерфейса Supplier, то сложность будет O(1).
         */
        Future<T> future = executorService.submit(() -> task.get());
        blockingQueue.add(future); // O(1)

        if (tasksAdded.incrementAndGet() == 3) { // Для завершения работы executorService
            executorService.shutdown();
            executorService.awaitTermination(4, TimeUnit.SECONDS);
        }
    }

    /**
     * Получить результат вычисления.
     * Возвращает результаты в том порядке, в котором добавлялись задачи.
     */
    public T getResult() throws ExecutionException, InterruptedException {
        /**
         * O(1), как было описано выше, удаление из связного списка последнего элемента очереди
         * дает константную сложность.
         */
        return blockingQueue.poll().get();
    }
}