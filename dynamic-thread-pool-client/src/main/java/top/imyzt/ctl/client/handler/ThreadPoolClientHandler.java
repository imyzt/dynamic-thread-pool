package top.imyzt.ctl.client.handler;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import top.imyzt.ctl.client.collection.ThreadPoolConfigReportHandler;

/**
 * @author imyzt
 * @date 2020/05/17
 * @description 客户端处理
 *
 * 1. 配置初始化线程池配置上报
 */
@Component
public class ThreadPoolClientHandler {

    private final ThreadPoolConfigReportHandler threadPoolConfigReportHandler;

    public ThreadPoolClientHandler(ThreadPoolConfigReportHandler threadPoolConfigReportHandler) {
        this.threadPoolConfigReportHandler = threadPoolConfigReportHandler;
    }

    /**
     * 启动时, 将本应用所有配置的动态线程池配置信息上报
     */
    @Bean
    ApplicationRunner initialReportThreadPool() {

        return args -> threadPoolConfigReportHandler.initialReport();
    }

}