package top.imyzt.ctl.client.clientsimple.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.imyzt.ctl.client.core.executor.DynamicThreadPoolTaskExecutor;

/**
 * @author imyzt
 * @date 2020/05/04
 * @description 线程池统一配置
 */
@Configuration
public class ThreadPoolConfiguration {

    @Bean
    public DynamicThreadPoolTaskExecutor printThread() {
        DynamicThreadPoolTaskExecutor executor = new DynamicThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("thread-print-");
        return executor;
    }


}