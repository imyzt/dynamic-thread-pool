package top.imyzt.ctl.client.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import top.imyzt.ctl.common.pojo.dto.ThreadPoolBaseInfo;
import top.imyzt.ctl.common.pojo.dto.ThreadPoolWorkState;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author imyzt
 * @date 2020/05/17
 * @description 描述信息
 */
@Component
@Slf4j
public class ThreadPoolUtils {

    private static ApplicationContext applicationContext;

    @Autowired
    public ThreadPoolUtils(ApplicationContext applicationContext) {
        ThreadPoolUtils.applicationContext = applicationContext;
    }

    public static void threadPoolStatus(ThreadPoolWorkState threadPoolConfig) {

        log.info("{}, 核心线程数={}, " +
                        "活动线程数={}, " +
                        "最大线程数={}, " +
                        "线程池活跃度={}, " +
                        "任务完成度={}, " +
                        "队列大小={}, " +
                        "当前排队线程数={}, " +
                        "队列剩余大小={}, " +
                        "队列使用度={} ",
                threadPoolConfig.getPoolName(),
                threadPoolConfig.getCorePoolSize(),
                threadPoolConfig.getActiveCount(),
                threadPoolConfig.getMaximumPoolSize(),
                threadPoolConfig.getActiveCount() / threadPoolConfig.getMaximumPoolSize(),
                threadPoolConfig.getCompletedTaskCount(),
                threadPoolConfig.getQueueSize() + threadPoolConfig.getQueueRemainingCapacity(),
                threadPoolConfig.getQueueSize(),
                threadPoolConfig.getQueueRemainingCapacity(),
                threadPoolConfig.getQueueSize() / (threadPoolConfig.getQueueSize() + threadPoolConfig.getQueueRemainingCapacity()));
    }


    /**
     * 调整线程池
     * @param newConfig 新配置
     */
    public static void editThreadPoolStatus (ThreadPoolBaseInfo newConfig) {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) applicationContext.getBean(newConfig.getPoolName());
        nonNullCheck(newConfig::getCorePoolSize, taskExecutor::setCorePoolSize);
        nonNullCheck(newConfig::getMaximumPoolSize, taskExecutor::setMaxPoolSize);
        nonNullCheck(newConfig::getQueueCapacity, taskExecutor::setQueueCapacity);
        nonNullCheck(newConfig::getKeepAliveSeconds, taskExecutor::setKeepAliveSeconds);
        nonNullCheck(newConfig::getThreadNamePrefix, taskExecutor::setThreadNamePrefix);
        log.info("线程池{}调整成功, 新配置={}", newConfig.getPoolName(), JSON.toJSONString(newConfig));
    }

    private static <T> void nonNullCheck(Supplier<T> get, Consumer<T> set) {
        T value = get.get();
        if (Objects.nonNull(value)) {
            set.accept(value);
        }
    }
}