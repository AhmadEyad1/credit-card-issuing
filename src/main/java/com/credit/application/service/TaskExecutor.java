package com.credit.application.service;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
public interface TaskExecutor {

    <T> CompletableFuture<T> submit(final Callable<T> task);
}