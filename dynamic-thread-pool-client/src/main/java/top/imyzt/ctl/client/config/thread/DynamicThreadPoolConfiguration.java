package top.imyzt.ctl.client.config.thread;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import top.imyzt.ctl.client.common.DynamicThreadPool;

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

        if (CollUtil.isEmpty(dynamicThreadPoolMap)) {
            log.warn("系统未配置动态线程池");
            return;
        }

        Map<String, Object> dynamicThreadPoolConfigBeans = applicationContext
                .getBeansWithAnnotation(DynamicThreadPool.class);

        if (CollUtil.isEmpty(dynamicThreadPoolConfigBeans)) {
            this.dynamicThreadPoolTaskExecutorMap = dynamicThreadPoolMap;
            log.warn("系统未查询到配置并开启动态处理的线程池");
            return;
        }

        this.dynamicThreadPoolTaskExecutorMap = Maps.newHashMap();
        dynamicThreadPoolMap.forEach((beanName, bean) -> {
            if (dynamicThreadPoolConfigBeans.containsKey(beanName)) {
                this.dynamicThreadPoolTaskExecutorMap.put(beanName, bean);
            }
        } );

        log.info("应用配置动态线程池数量, size={}", dynamicThreadPoolMap.size());
    }

}