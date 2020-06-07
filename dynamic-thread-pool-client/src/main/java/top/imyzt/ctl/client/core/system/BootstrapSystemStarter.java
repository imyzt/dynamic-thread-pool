package top.imyzt.ctl.client.core.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.imyzt.ctl.client.handler.ThreadPoolConfigReportHandler;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author imyzt
 * @date 2020/05/24
 * @description 系统启动
 */
@Component
@Slf4j
@Configuration
@EnableScheduling
@EnableAsync
public class BootstrapSystemStarter {

    @Resource
    private ThreadPoolConfigReportHandler threadPoolConfigReportHandler;

    /**
     * 启动时, 交由不同线程处理不同内容
     */
    @PostConstruct
    void initial() {

        // 启动时, 将本应用所有配置的动态线程池配置信息上报
        // 初始上报线程池配置信息
        threadPoolConfigReportHandler.initialReport();

        // 长连接任务, 监听线程改变, 修改线程池配置
        threadPoolConfigReportHandler.configChangeMonitor();
    }

    /**
     * 定时上报任务
     */
    @Scheduled(fixedRateString = "${spring.dynamic-thread-pool.fixed-rate}")
    public void timingReport () {
        threadPoolConfigReportHandler.timingReport();
    }

}