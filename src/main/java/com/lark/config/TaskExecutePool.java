package com.lark.config;

import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class TaskExecutePool {
    @Value("${lark.task.maxPoolSize}")
    private Integer maxPoolSize;
    @Value("${lark.task.queueCapacity}")
    private Integer queueCapacity;

    public TaskExecutePool() {
    }

    @Bean
    public Executor taskExecutor() {
        return this.getExecutor(5, this.maxPoolSize, this.queueCapacity, 60);
    }

    private Executor getExecutor(int corePoolSize, int maxPoolSize, int queueCapacity, int keepAliveSeconds) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;
    }
}
