package top.imyzt.ctl.client.config.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author imyzt
 * @date 2020/05/04
 * @description 动态线程池管理配置中心
 */
@Configuration
public class DynamicThreadPoolConfiguration {

    private static final Logger log = LoggerFactory.getLogger(DynamicThreadPoolConfiguration.class);
    private final ApplicationContext applicationContext;

    @Value("${spring.application.name}")
    private String appName;
    @Value("${spring.dynamic-thread-pool.server-url}")
    private String serverUrl;

    /**
     * 当前应用实例所有动态线程池实例
     */
    private Map<String, ThreadPoolTaskExecutor> dynamicThreadPoolTaskExecutorMap;

    public DynamicThreadPoolConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Map<String, ThreadPoolTaskExecutor> getDynamicThreadPoolTaskExecutorMap() {
        return dynamicThreadPoolTaskExecutorMap;
    }

    /**
     * 启动时, 将所有配置了动态线程池的线程池实例注册到 {@link DynamicThreadPoolConfiguration#dynamicThreadPoolTaskExecutorMap}
     */
    @PostConstruct
    public void initialRegistrationThreadPool() {

        // 获得所有动态线程池实例
        Map<String, ThreadPoolTaskExecutor> dynamicThreadPoolMap =
                applicationContext.getBeansOfType(ThreadPoolTaskExecutor.class);

        log.info("应用配置动态线程池数量, size={}", dynamicThreadPoolMap.size());

        this.dynamicThreadPoolTaskExecutorMap = dynamicThreadPoolMap;
    }

}