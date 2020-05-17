package top.imyzt.ctl.client.config.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.PeriodicTrigger;
import top.imyzt.ctl.client.collection.ThreadPoolConfigReportHandler;
import top.imyzt.ctl.client.properties.DynamicThreadPoolProperties;

/**
 * @author imyzt
 * @date 2020/05/04
 * @description 定时任务配置
 */
@Slf4j
@Configuration
@EnableScheduling
public class CompleteScheduleConfig implements SchedulingConfigurer {

    private final DynamicThreadPoolProperties dynamicThreadPoolProperties;
    private final ThreadPoolConfigReportHandler threadPoolConfigReportHandler;

    public CompleteScheduleConfig(DynamicThreadPoolProperties dynamicThreadPoolProperties,
                                  ThreadPoolConfigReportHandler threadPoolConfigReportHandler) {
        this.dynamicThreadPoolProperties = dynamicThreadPoolProperties;
        this.threadPoolConfigReportHandler = threadPoolConfigReportHandler;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                // TODO: 2020/5/4 可以增加异步线程池, 保证采集线程不被上报线程阻塞
                threadPoolConfigReportHandler::scheduleReport,
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    long cycleMilliSeconds = dynamicThreadPoolProperties.getCycle().toMillis();
                    return new PeriodicTrigger(cycleMilliSeconds).nextExecutionTime(triggerContext);
                }
        );
    }
}