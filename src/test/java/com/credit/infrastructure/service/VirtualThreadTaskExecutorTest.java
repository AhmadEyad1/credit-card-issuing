package com.credit.infrastructure.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@SpringBootTest
@ActiveProfiles("test")
public class VirtualThreadTaskExecutorTest {

    @Autowired
    private VirtualThreadTaskExecutor virtualThreadTaskExecutor;

    @Test
    void shouldExecuteTaskSuccessfully() throws Exception {
        final Callable<String> task = () -> "Task Result";

        final CompletableFuture<String> future = virtualThreadTaskExecutor.submit(task);
        final String result = future.get();

        assertEquals("Task Result", result);
    }


    @Test
    void shouldThrowExceptionWhenTaskFails() {
        final Callable<String> failingTask = () -> {
            throw new RuntimeException("Task Failure");
        };

        final CompletableFuture<String> future = virtualThreadTaskExecutor.submit(failingTask);
        final ExecutionException executionException = assertThrows(ExecutionException.class, future::get);

        assertInstanceOf(RuntimeException.class, executionException.getCause());
        assertEquals("Error in task execution", executionException.getCause().getMessage());
    }

    @Test
    void shouldExecuteMultipleTasksSimultaneously() throws InterruptedException, ExecutionException {
        final int taskCount = 5;
        final CountDownLatch latch = new CountDownLatch(taskCount);
        final CountDownLatch allTasksReady = new CountDownLatch(1);

        final List<Callable<String>> tasks = IntStream.range(0, taskCount)
                .mapToObj(i -> (Callable<String>) () -> {
                    allTasksReady.await();
                    Thread.sleep(100);
                    latch.countDown();
                    return "Task " + i;
                })
                .toList();

        final long startTime = System.currentTimeMillis();
        final List<CompletableFuture<String>> futures = tasks.stream()
                .map(virtualThreadTaskExecutor::submit)
                .toList();

        allTasksReady.countDown();

        latch.await();

        final long endTime = System.currentTimeMillis();
        final long elapsedTime = endTime - startTime;

        for (int i = 0; i < taskCount; i++) {
            assertEquals("Task " + i, futures.get(i).get(), "Task Result Mismatched");
        }

        assertTrue(elapsedTime < 200, "Tasks did not run concurrently as expected.");
    }
}
