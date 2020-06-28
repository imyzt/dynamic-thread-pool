package top.imyzt.ctl.server.listener;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import top.imyzt.ctl.common.pojo.dto.info.ThreadPoolBaseInfo;
import top.imyzt.ctl.common.pojo.dto.ThreadPoolConfigReportBaseInfo;
import top.imyzt.ctl.common.pojo.dto.info.ThreadPoolWorkState;

import java.io.Serializable;
import java.util.List;

/**
 * @author imyzt
 * @date 2020/06/21
 * @description 线程池状态数据动态上报, 可以在此对其监听, 根据该线程池配置的数据, 进行具体的处理
 *
 * 1. 报警通知
 * 2. 自动扩容
 */
@Component
@Slf4j
public class PoolReportListener {

    @EventListener
    public void listener(CollectionReportEvent event) {

        ThreadPoolConfigReportBaseInfo baseInfo = event.getBaseInfo();

        List<ThreadPoolWorkState> threadPoolConfigList = baseInfo.getThreadPoolConfigList();

        // 遍历每一个线程池, 确定是否有配置报警. 自动扩容策略
        for (ThreadPoolBaseInfo threadPoolBaseInfo : threadPoolConfigList) {

            String poolName = threadPoolBaseInfo.getPoolName();


        }

        log.info("collection");
    }


    @Getter
    public static class CollectionReportEvent extends ApplicationEvent implements Serializable {

        private ThreadPoolConfigReportBaseInfo baseInfo;

        public CollectionReportEvent(Object source, ThreadPoolConfigReportBaseInfo baseInfo) {
            super(source);
            this.baseInfo = baseInfo;
        }
    }
}