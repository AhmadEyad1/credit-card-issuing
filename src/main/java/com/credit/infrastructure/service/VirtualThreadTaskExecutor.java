package com.credit.infrastructure.service;

import com.credit.application.service.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Component
public class VirtualThreadTaskExecutor implements TaskExecutor {

    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    @Override
    public <T> CompletableFuture<T> submit(final Callable<T> task) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return task.call();
            } catch (Exception e) {
                throw new RuntimeException("Error in task execution", e);
            }
        }, executor);
    }
}