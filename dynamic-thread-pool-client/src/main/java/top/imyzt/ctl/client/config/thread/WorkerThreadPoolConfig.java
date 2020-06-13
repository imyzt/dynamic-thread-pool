package top.imyzt.ctl.client.config.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.imyzt.ctl.client.core.executor.DynamicThreadPoolTaskExecutor;

/**
 * @author imyzt
 * @date 2020/05/05
 * @description 工作线程池配置统一管理
 */
@Configuration
@Slf4j
public class WorkerThreadPoolConfig {

    /**
     * 采集上报线程池
     */
    @Bean("collectionThreadPool")
    public DynamicThreadPoolTaskExecutor collectionThreadPool() {
        DynamicThreadPoolTaskExecutor executor = new DynamicThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(1024);
        executor.setThreadNamePrefix("thread-pool-collection-");
        executor.setKeepAliveSeconds(60);
        return executor;
    }

    /**
     * 监听服务端线程池配置变化
     */
    @Bean("configChangeMonitor")
    public DynamicThreadPoolTaskExecutor configChangeMonitor() {
        DynamicThreadPoolTaskExecutor executor = new DynamicThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setQueueCapacity(16);
        executor.setThreadNamePrefix("config-change-monitor-");
        return executor;
    }

    /**
     * 修改线程池配置
     */
    @Bean("configChange")
    public DynamicThreadPoolTaskExecutor configChange() {
        DynamicThreadPoolTaskExecutor executor = new DynamicThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setQueueCapacity(16);
        executor.setThreadNamePrefix("config-change-");
        return executor;
    }
}