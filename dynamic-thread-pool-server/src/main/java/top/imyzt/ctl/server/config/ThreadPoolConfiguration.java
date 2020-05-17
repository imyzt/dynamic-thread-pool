package top.imyzt.ctl.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author imyzt
 * @date 2020/05/10
 * @description 线程池配置
 */
@Configuration
@Slf4j
public class ThreadPoolConfiguration {

    /**
     * 服务端监听线程池改变时, 响应客户端长连接的池
     */
    @Bean("threadPoolConfigChangeMonitor")
    public ThreadPoolTaskExecutor threadPoolConfigChanceMonitor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(10000);
        executor.setThreadNamePrefix("thread-pool-config-chance-monitor-");
        return executor;
    }
}