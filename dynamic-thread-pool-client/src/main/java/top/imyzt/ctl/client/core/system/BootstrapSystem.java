package top.imyzt.ctl.client.core.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.imyzt.ctl.client.handler.ThreadPoolConfigReportHandler;

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
public class BootstrapSystem {

    @Resource
    private ThreadPoolConfigReportHandler threadPoolConfigReportHandler;

    /**
     * 启动时, 将本应用所有配置的动态线程池配置信息上报
     * 初始上报线程池配置信息
     */
    @Bean
    ApplicationRunner initialReportThreadPool() {

        return args -> threadPoolConfigReportHandler.initialReport();
    }

    /**
     * 定时上报任务
     */
    @Scheduled(fixedRateString = "${spring.dynamic-thread-pool.fixed-rate}")
    public void timingReport () {
        threadPoolConfigReportHandler.timingReport();
    }

    /**
     * 长连接任务, 监听线程改变, 修改线程池配置
     */
    @Bean
    ApplicationRunner configChangeMonitor() {

        return args -> threadPoolConfigReportHandler.configChangeMonitor();
    }
}