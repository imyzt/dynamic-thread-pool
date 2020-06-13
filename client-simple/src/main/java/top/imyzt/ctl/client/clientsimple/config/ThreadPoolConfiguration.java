package top.imyzt.ctl.client.clientsimple.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author imyzt
 * @date 2020/05/04
 * @description 线程池统一配置
 */
@Configuration
public class ThreadPoolConfiguration {

    @Bean
    public ThreadPoolTaskExecutor printThread() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("thread-print-");
        return executor;
    }


}