package top.imyzt.ctl.client.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import top.imyzt.ctl.common.pojo.dto.ThreadPoolBaseInfo;
import top.imyzt.ctl.common.pojo.dto.ThreadPoolWorkState;
import top.imyzt.ctl.common.utils.JsonUtils;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author imyzt
 * @date 2020/05/17
 * @description 线程池工具类
 */
@Component
@Slf4j
public class ThreadPoolUtils {

    private static ApplicationContext applicationContext;

    @Autowired
    public ThreadPoolUtils(ApplicationContext applicationContext) {
        ThreadPoolUtils.applicationContext = applicationContext;
    }

    /**
     * 答应线程池当前状态
     */
    public static void printThreadPoolStatus(ThreadPoolWorkState threadPoolConfig) {

        String taskCompletion = divide(threadPoolConfig.getActiveCount(), threadPoolConfig.getMaximumPoolSize());
        String queueUsage = divide(threadPoolConfig.getQueueSize(), threadPoolConfig.getQueueSize() + threadPoolConfig.getQueueRemainingCapacity());

        log.debug("{}, 核心线程数={}, " +
                        "活动线程数={}, " +
                        "最大线程数={}, " +
                        "队列大小={}, " +

                        "任务完成数={}, " +
                        "线程池活跃度={}, " +
                        "当前排队线程数={}, " +
                        "队列剩余大小={}, " +
                        "队列使用情况={} ",
                threadPoolConfig.getPoolName(),
                threadPoolConfig.getCorePoolSize(),
                threadPoolConfig.getActiveCount(),
                threadPoolConfig.getMaximumPoolSize(),
                threadPoolConfig.getQueueSize() + threadPoolConfig.getQueueRemainingCapacity(),

                threadPoolConfig.getCompletedTaskCount(),
                taskCompletion,
                threadPoolConfig.getQueueSize(),
                threadPoolConfig.getQueueRemainingCapacity(),
                queueUsage);
    }

    /**
     * 除法, 保留两位小数
     */
    private static String divide(int num1, int num2) {
        return String.format("%1.2f%%",  Double.parseDouble(num1 + "")
                / Double.parseDouble(num2 + "") * 100);
    }


    /**
     * 调整线程池
     * @see ThreadPoolBaseInfo#getPoolName() 通过名称修改线程
     * @param newConfig 新配置
     */
    public static void editThreadPoolStatus (ThreadPoolBaseInfo newConfig) {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) applicationContext.getBean(newConfig.getPoolName());
        nonNullCheck(newConfig::getCorePoolSize, taskExecutor::setCorePoolSize);
        nonNullCheck(newConfig::getMaximumPoolSize, taskExecutor::setMaxPoolSize);
        nonNullCheck(newConfig::getQueueCapacity, taskExecutor::setQueueCapacity);
        nonNullCheck(newConfig::getKeepAliveSeconds, taskExecutor::setKeepAliveSeconds);
        nonNullCheck(newConfig::getThreadNamePrefix, taskExecutor::setThreadNamePrefix);
        log.info("线程池{}调整成功, 新配置={}", newConfig.getPoolName(), JsonUtils.toJsonString(newConfig));
    }

    private static <T> void nonNullCheck(Supplier<T> get, Consumer<T> set) {
        T value = get.get();
        if (Objects.nonNull(value)) {
            set.accept(value);
        }
    }
}