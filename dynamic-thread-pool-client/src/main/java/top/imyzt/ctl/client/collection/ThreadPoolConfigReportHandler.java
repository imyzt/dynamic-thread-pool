package top.imyzt.ctl.client.collection;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.imyzt.ctl.client.config.thread.DynamicThreadPoolConfiguration;
import top.imyzt.ctl.client.core.executor.DynamicThreadPoolTaskExecutor;
import top.imyzt.ctl.client.core.queue.ResizeCapacityLinkedBlockingQueue;
import top.imyzt.ctl.client.utils.ThreadPoolUtils;
import top.imyzt.ctl.common.pojo.dto.ThreadPoolBaseInfo;
import top.imyzt.ctl.common.pojo.dto.ThreadPoolConfigReportBaseInfo;
import top.imyzt.ctl.common.pojo.dto.ThreadPoolConfigReportInfo;
import top.imyzt.ctl.common.pojo.dto.ThreadPoolWorkState;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import static java.net.InetAddress.getLocalHost;

/**
 * @author imyzt
 * @date 2020/05/04
 * @description 线程池配置上报处理类
 */
@Component
@Slf4j
public class ThreadPoolConfigReportHandler {

    @Value("${spring.application.name}")
    private String appName;
    @Value("${server.port}")
    private Integer appPort;
    @Value("${spring.dynamic-thread-pool.server-url}")
    private String serverUrl;

    private final DynamicThreadPoolConfiguration dynamicThreadPoolConfiguration;

    public ThreadPoolConfigReportHandler(DynamicThreadPoolConfiguration dynamicThreadPoolConfiguration) {
        this.dynamicThreadPoolConfiguration = dynamicThreadPoolConfiguration;
    }

    /**
     * 定时采集数据上报
     */
    public void scheduleReport() {

        Map<String, DynamicThreadPoolTaskExecutor> dynamicThreadPoolMap =
                dynamicThreadPoolConfiguration.getDynamicThreadPoolTaskExecutorMap();

        ThreadPoolConfigReportInfo dto = getDynamicThreadPoolReportInfo();

        List<ThreadPoolBaseInfo> threadPoolWorkStateList = buildThreadPoolWorkStateList(dynamicThreadPoolMap);

        dto.setThreadPoolInfo(threadPoolWorkStateList);

        // 数据上报

        String currNewVersion = sendToServer(dto);

        log.info("定时上报任务上报完成, 服务器端返回最新版本={}", currNewVersion);
    }

    /**
     * 初始上报
     */
    public void initialReport() {

        Map<String, DynamicThreadPoolTaskExecutor> dynamicThreadPoolTaskExecutorMap =
                dynamicThreadPoolConfiguration.getDynamicThreadPoolTaskExecutorMap();

        ArrayList<ThreadPoolBaseInfo> threadPoolConfigList = new ArrayList<>(dynamicThreadPoolTaskExecutorMap.size());

        dynamicThreadPoolTaskExecutorMap.forEach((tName, executor) -> {

            ThreadPoolBaseInfo threadPoolConfig = new ThreadPoolBaseInfo();

            // 线程池的基础配置信息
            buildBaseThreadPoolConfig(tName, executor, threadPoolConfig);

            threadPoolConfigList.add(threadPoolConfig);
        });

        ThreadPoolConfigReportBaseInfo dto = new ThreadPoolConfigReportBaseInfo(appName, threadPoolConfigList);

        String currNewVersion = sendToServer(dto);

        log.info("初始化信息上报完成, 服务器端返回最新版本={}", currNewVersion);
    }

    /**
     * 发送信息到服务端
     * @return 服务端最新版本
     */
    private String sendToServer(ThreadPoolConfigReportBaseInfo dto) {
        HttpResponse response = HttpRequest.post(serverUrl + "/config/init")
                .body(JSON.toJSONString(dto))
                .contentType("application/json")
                .execute();
        return response.body();
    }


    /**
     * 信息上报基础信息
     */
    private ThreadPoolConfigReportInfo getDynamicThreadPoolReportInfo() {

        ThreadPoolConfigReportInfo dto = new ThreadPoolConfigReportInfo();

        dto.setAppName(appName);
        dto.setAppPort(appPort);

        String hostName;
        try {
            hostName = getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            hostName = null;
        }
        dto.setInstantName(hostName);
        return dto;
    }

    private List<ThreadPoolBaseInfo> buildThreadPoolWorkStateList(Map<String, DynamicThreadPoolTaskExecutor> dynamicThreadPoolMap) {

        List<ThreadPoolBaseInfo> threadPoolWorkStateList = new ArrayList<>();

        dynamicThreadPoolMap.forEach((tName, executor) -> {

            ThreadPoolWorkState threadPoolConfig = new ThreadPoolWorkState();

            // 线程池的基础配置信息
            buildBaseThreadPoolConfig(tName, executor, threadPoolConfig);

            ThreadPoolExecutor threadPoolExecutor = executor.getThreadPoolExecutor();
            // 运行中线程池的基本信息
            threadPoolConfig.setActiveCount(executor.getActiveCount());
            threadPoolConfig.setQueueRemainingCapacity(threadPoolExecutor.getQueue().remainingCapacity());
            threadPoolConfig.setQueueType(threadPoolExecutor.getQueue().getClass().getSimpleName());
            threadPoolConfig.setCompletedTaskCount(threadPoolExecutor.getCompletedTaskCount());
            threadPoolConfig.setLargestPoolSize(threadPoolExecutor.getLargestPoolSize());
            threadPoolConfig.setTaskCount(threadPoolExecutor.getTaskCount());

            threadPoolConfig.setPoolSize(executor.getPoolSize());
            threadPoolConfig.setQueueSize(threadPoolExecutor.getQueue().size());

            ThreadPoolUtils.threadPoolStatus(threadPoolConfig);

            threadPoolWorkStateList.add(threadPoolConfig);
        });
        return threadPoolWorkStateList;
    }

    /**
     * 构建线程池配置的基础信息
     */
    private <T extends ThreadPoolBaseInfo> void buildBaseThreadPoolConfig (String tName, DynamicThreadPoolTaskExecutor dynamicExecutor, T dto) {

        // 线程池的基础配置
        dto.setCorePoolSize(dynamicExecutor.getCorePoolSize());
        dto.setMaximumPoolSize(dynamicExecutor.getMaxPoolSize());
        dto.setPoolName(tName);
        dto.setThreadNamePrefix(dynamicExecutor.getThreadNamePrefix());
        dto.setKeepAliveSeconds(dynamicExecutor.getKeepAliveSeconds());

        ThreadPoolExecutor threadPoolExecutor = dynamicExecutor.getThreadPoolExecutor();

        dto.setQueueType(threadPoolExecutor.getQueue().getClass().getSimpleName());
        // 队列的capacity在原生的ThreadPoolExecutor#BlockingQueue中是final修饰的, 重写的ResizeCapacityLinkedBlockingQueue移除了final
        BlockingQueue<Runnable> queue = threadPoolExecutor.getQueue();
        if (queue instanceof ResizeCapacityLinkedBlockingQueue) {
            int capacity = ((ResizeCapacityLinkedBlockingQueue) threadPoolExecutor.getQueue()).getCapacity();
            dto.setQueueCapacity(capacity);
        }
    }
}