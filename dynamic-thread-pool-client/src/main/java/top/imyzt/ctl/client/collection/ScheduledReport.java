package top.imyzt.ctl.client.collection;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.imyzt.ctl.client.config.DynamicThreadPoolConfiguration;
import top.imyzt.ctl.client.core.executor.DynamicThreadPoolTaskExecutor;
import top.imyzt.ctl.common.pojo.dto.DynamicThreadPoolReportDTO;
import top.imyzt.ctl.common.pojo.dto.ThreadPoolWorkStateDTO;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import static java.net.InetAddress.getLocalHost;

/**
 * @author imyzt
 * @date 2020/05/04
 * @description 定时上报服务
 */
@Component
@Slf4j
public class ScheduledReport {

    @Value("${spring.application.name}")
    private String appName;
    @Value("${server.port}")
    private Integer appPort;
    @Value("${spring.dynamic.server-url}")
    private String serverUrl;

    private final DynamicThreadPoolConfiguration dynamicThreadPoolConfiguration;

    public ScheduledReport(DynamicThreadPoolConfiguration dynamicThreadPoolConfiguration) {
        this.dynamicThreadPoolConfiguration = dynamicThreadPoolConfiguration;
    }

    /**
     * 采集数据上报
     */
    public void collection() {

        log.info("定时上报任务");

        Map<String, DynamicThreadPoolTaskExecutor> dynamicThreadPoolMap =
                dynamicThreadPoolConfiguration.getDynamicThreadPoolTaskExecutorMap();

        DynamicThreadPoolReportDTO dto = new DynamicThreadPoolReportDTO();

        ArrayList<ThreadPoolWorkStateDTO> threadPoolWorkStateList = buildThreadPoolWorkStateList(dynamicThreadPoolMap);

        dto.setWorkStateList(threadPoolWorkStateList);
        dto.setAppName(appName);
        dto.setInstantName(getInstantName());
        dto.setAppPort(appPort);



        // 数据上报
        HttpRequest.post(serverUrl + "/collection")
                .body(JSON.toJSONString(dto))
                .execute();

    }

    private String getInstantName() {

        try {
            return getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "";
        }
    }

    private ArrayList<ThreadPoolWorkStateDTO> buildThreadPoolWorkStateList(Map<String, DynamicThreadPoolTaskExecutor> dynamicThreadPoolMap) {
        ArrayList<ThreadPoolWorkStateDTO> threadPoolWorkStateList = new ArrayList<>();
        dynamicThreadPoolMap.forEach((tName, executor) -> {

            ThreadPoolExecutor tpe = executor.getThreadPoolExecutor();

            ThreadPoolWorkStateDTO build = ThreadPoolWorkStateDTO.builder()
                    .poolName(executor.getThreadNamePrefix())
                    .corePoolSize(executor.getCorePoolSize())
                    .maximumPoolSize(executor.getMaxPoolSize())
                    .poolSize(executor.getPoolSize())
                    .activeCount(executor.getActiveCount())
                    .keepAliveSeconds(executor.getKeepAliveSeconds())

                    .queueType(tpe.getQueue().getClass().getSimpleName())
                    .queueSize(tpe.getQueue().size())
//                    .queueCapacity(executor.getQueueCapacity())
                    .queueRemainingCapacity(tpe.getQueue().remainingCapacity())
                    .completedTaskCount(tpe.getCompletedTaskCount())
                    .largestPoolSize(tpe.getLargestPoolSize())
//                    .rejectCount()

                    .build();

            threadPoolWorkStateList.add(build);
        });
        return threadPoolWorkStateList;
    }



}