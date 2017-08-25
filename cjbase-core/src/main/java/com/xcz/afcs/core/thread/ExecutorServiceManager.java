package com.xcz.afcs.core.thread;

import com.xcz.afcs.core.context.DataContext;
import com.xcz.afcs.util.CollectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExecutorServiceManager {
    
    public static final int DEFAULT_POOL_SIZE = 10;
    
    private static Map<String, ExecutorService> executorServiceMap = new HashMap<String, ExecutorService>();
    
    private static Lock executorServiceMapLock = new ReentrantLock();
    
    static {
        init();
    }
    
    private static void init() {
        // 注册关闭钩子
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                executorServiceMapLock.lock();
                try {
                    for (ExecutorService executorService : executorServiceMap.values()) {
                        executorService.shutdown();
                    }
                } finally {
                    executorServiceMapLock.unlock();
                }
            }
        });
    }
    
    private static ExecutorService getThreadPool(int corePoolSize, String name,
            ExecutorServiceFactory executorServiceFactory) {
        ExecutorService executorService = executorServiceMap.get(name);
        executorServiceMapLock.lock();
        try {
            executorService = executorServiceMap.get(name);
            if (null == executorService) {
                // 创建执行服务
                executorService = executorServiceFactory.createExecutorService(corePoolSize, name);
                executorServiceMap.put(name, executorService);
            }
        } finally {
            executorServiceMapLock.unlock();
        }
        return executorService;
    }
    
    private static abstract class ExecutorServiceFactory {
        
        public ExecutorService createExecutorService(int corePoolSize, String threadGroupName) {
            return createExecutorService(corePoolSize, createThreadFactory(threadGroupName));
        }
        
        public abstract ExecutorService createExecutorService(int corePoolSize, ThreadFactory threadFactory);
        
    }
    
    private static ExecutorServiceFactory cachedThreadPoolFactory = new ExecutorServiceFactory() {
        @Override
        public ExecutorService createExecutorService(int corePoolSize, ThreadFactory threadFactory) {
            return Executors.newCachedThreadPool(threadFactory);
        };
    };
    
    private static ExecutorServiceFactory scheduledThreadPoolFactory = new ExecutorServiceFactory() {
        @Override
        public ExecutorService createExecutorService(int corePoolSize, ThreadFactory threadFactory) {
            return Executors.newScheduledThreadPool(corePoolSize, threadFactory);
        }
    };
    
    private static ExecutorServiceFactory fixedThreadPoolFactory = new ExecutorServiceFactory() {
        @Override
        public ExecutorService createExecutorService(int corePoolSize, ThreadFactory threadFactory) {
            return Executors.newFixedThreadPool(corePoolSize, threadFactory);
        }
    };
    
    private static ExecutorServiceFactory singleThreadPoolFactory = new ExecutorServiceFactory() {
        @Override
        public ExecutorService createExecutorService(int corePoolSize, ThreadFactory threadFactory) {
            return Executors.newSingleThreadExecutor(threadFactory);
        }
    };
    
    private static ExecutorServiceFactory singleScheduledThreadPoolFactory = new ExecutorServiceFactory() {
        @Override
        public ExecutorService createExecutorService(int corePoolSize, ThreadFactory threadFactory) {
            return Executors.newSingleThreadScheduledExecutor(threadFactory);
        }
    };
    
    public static ExecutorService getCachedThreadPool(String name) {
        return getThreadPool(0, name, cachedThreadPoolFactory);
    }
    
    public static ScheduledExecutorService getScheduledThreadPool(String name) {
        return getScheduledThreadPool(DEFAULT_POOL_SIZE, name);
    }
    
    public static ScheduledExecutorService getScheduledThreadPool(int corePoolSize, String name) {
        return (ScheduledExecutorService) getThreadPool(corePoolSize, name, scheduledThreadPoolFactory);
    }
    
    public static ExecutorService getFixedThreadPool(int nThreads, String name) {
        return getThreadPool(nThreads, name, fixedThreadPoolFactory);
    }
    
    public static ExecutorService getSingleThreadPool(String name) {
        return getThreadPool(0, name, singleThreadPoolFactory);
    }
    
    public static ScheduledExecutorService getSingleScheduledThreadPool(String name) {
        return (ScheduledExecutorService) getThreadPool(0, name, singleScheduledThreadPoolFactory);
    }
    
    private static ThreadFactory createThreadFactory(final String threadGroupName) {
        return new ThreadFactory() {
            private String threadNamePrefix = threadGroupName + "Thread-";
            private AtomicInteger threadIndex = new AtomicInteger();
            
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName(threadNamePrefix + (threadIndex.incrementAndGet()));
                t.setDaemon(true);
                return t;
            }
        };
    }
    
    public static <T> Future<T> submit(ExecutorService executorService, final Callable<T> task,
            String... penetratedContextKeys) {
        // 判断是否需要复制上下文变量
        final Map<String, Object> penetratedContext = preparePenetratedContext(penetratedContextKeys);
        return executorService.submit(new Callable<T>() {
            @SuppressWarnings("unchecked")
            @Override
            public T call() throws Exception {
                if (null != penetratedContext) {
                    // 复制上下文变量
                    try {
                        CollectionUtil.merge(DataContext.getStore(), penetratedContext);
                        return task.call();
                    } finally {
                        DataContext.clear();
                    }
                } else {
                    // 无需复制上下文变量
                    return task.call();
                }
            }
        });
    }
    
    public static Future<?> submit(ExecutorService executorService, Runnable task, String... penetratedContextKeys) {
        return submit(executorService, task, null, penetratedContextKeys);
    }
    
    public static <T> Future<T> submit(ExecutorService executorService, final Runnable task, T result,
            String... penetratedContextKeys) {
        // 判断是否需要复制上下文变量
        final Map<String, Object> penetratedContext = preparePenetratedContext(penetratedContextKeys);
        return executorService.submit(new Runnable() {
            @SuppressWarnings("unchecked")
            @Override
            public void run() {
                if (null != penetratedContext) {
                    // 复制上下文变量
                    try {
                        CollectionUtil.merge(DataContext.getStore(), penetratedContext);
                        task.run();
                    } finally {
                        DataContext.clear();
                    }
                } else {
                    // 无需复制上下文变量
                    task.run();
                }
            }
        }, result);
    }
    
    private static Map<String, Object> preparePenetratedContext(String... penetratedContextKeys) {
        Map<String, Object> penetratedContext;
        if (null != penetratedContextKeys && 0 != penetratedContextKeys.length) {
            penetratedContext = CollectionUtil.subMap(DataContext.getStore(), penetratedContextKeys);
        } else {
            penetratedContext = null;
        }
        return penetratedContext;
    }

}
