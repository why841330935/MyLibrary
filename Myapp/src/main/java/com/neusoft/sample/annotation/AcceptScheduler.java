package com.neusoft.sample.annotation;

import java.util.concurrent.Executor;

import rx.Scheduler;

/**
 * on 2016/8/23.
 * Created by WangHuanyu 外包
 */
public enum AcceptScheduler {
    /**
     * {@link Scheduler} which will execute actions on the Android UI thread.
     */
    MAIN_THREAD,

    /**
     * Creates and returns a {@link Scheduler} that creates a new {@link Thread} for each unit of work.
     * <p>
     * Unhandled errors will be delivered to the scheduler Thread's {@link java.lang.Thread.UncaughtExceptionHandler}.
     */
    NEW_THREAD,

    /**
     * Creates and returns a {@link Scheduler} intended for IO-bound work.
     * <p>
     * The implementation is backed by an {@link Executor} thread-pool that will grow as needed.
     * <p>
     * This can be used for asynchronously performing blocking IO.
     * <p>
     * Do not perform computational work on this scheduler. Use computation() instead.
     * <p>
     * Unhandled errors will be delivered to the scheduler Thread's {@link java.lang.Thread.UncaughtExceptionHandler}.
     */
    IO,

    /**
     * Creates and returns a {@link Scheduler} intended for computational work.
     * <p>
     * This can be used for event-loops, processing callbacks and other computational work.
     * <p>
     * Do not perform IO-bound work on this scheduler. Use io() instead.
     * <p>
     * Unhandled errors will be delivered to the scheduler Thread's {@link java.lang.Thread.UncaughtExceptionHandler}.
     */
    COMPUTATION,

    /**
     * Creates and returns a {@link Scheduler} that queues work on the current thread to be executed after the
     * current work completes.
     */
    TRAMPOLINE,

    /**
     * Creates and returns a {@link Scheduler} that executes work immediately on the current thread.
     */
    IMMEDIATE,

}
