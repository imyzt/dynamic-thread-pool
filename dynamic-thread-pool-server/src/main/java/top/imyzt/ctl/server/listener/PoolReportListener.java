package top.imyzt.ctl.server.listener;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import top.imyzt.ctl.common.pojo.dto.ThreadPoolConfigReportBaseInfo;
import top.imyzt.ctl.common.pojo.dto.info.ThreadPoolBaseInfo;
import top.imyzt.ctl.common.pojo.dto.info.ThreadPoolWorkState;
import top.imyzt.ctl.server.pojo.dto.AlarmExpansionRuleDTO;
import top.imyzt.ctl.server.pojo.dto.ExpansionRuleDTO;
import top.imyzt.ctl.server.service.AlarmRuleService;
import top.imyzt.ctl.server.service.ConfigService;
import top.imyzt.ctl.server.utils.JsonUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

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

    @Resource
    private AlarmRuleService alarmRuleService;
    @Resource
    private ConfigService configService;

    @EventListener
    @SuppressWarnings("unchecked")
    public void listener(CollectionReportEvent event) {

        ThreadPoolConfigReportBaseInfo baseInfo = event.getBaseInfo();
        String appName = baseInfo.getAppName();

        List<ThreadPoolWorkState> threadPoolConfigList = baseInfo.getThreadPoolConfigList();

        // 遍历每一个线程池, 确定是否有配置报警. 自动扩容策略
        for (ThreadPoolWorkState workState : threadPoolConfigList) {

            String poolName = workState.getPoolName();

            List<AlarmExpansionRuleDTO> ruleList = alarmRuleService.getRuleList(appName, poolName);
            if (CollectionUtils.isEmpty(ruleList)) {
                continue;
            }

            // 报警规则 / 扩容规则
            for (AlarmExpansionRuleDTO rule : ruleList) {

                boolean check = check(workState, rule);

                if (check) {
                    thresholdReached(appName, workState, rule);
                }
            }
        }
    }

    /**
     * 检查是否达到阈值
     */
    private boolean check(ThreadPoolWorkState workState, AlarmExpansionRuleDTO rule) {

        double taskCompletion = divide(workState.getActiveCount(), workState.getMaximumPoolSize());
        double queueUsage = divide(workState.getQueueSize(), workState.getQueueSize() + workState.getQueueRemainingCapacity());

        // 某一项达到阈值
        return taskCompletion >= rule.getTaskCompletion() || queueUsage >= rule.getQueueUsage();
    }

    /**
     * 达到阈值之后, 处理推送和扩容
     */
    private void thresholdReached(String appName, ThreadPoolWorkState workState, AlarmExpansionRuleDTO rule) {

        boolean hasExpansionRule = Objects.nonNull(rule.getExpansionRule());

        // 如果配置了扩容规则, 直接扩容, 然后发送扩容成功信息
        // 如果没有扩容规则, 则按照通知规则, 发送规则通知

        if (hasExpansionRule) {

            // 扩容
            this.publishConfig(appName, workState, rule);

            log.info("扩容成功, appName={}, workState={}, rule={}", appName, JsonUtils.toJson(workState), JsonUtils.toJson(rule));
        }

        // 发送消息
        log.info("达到报警阈值, appName={}, workState={}, rule={}", appName, JsonUtils.toJson(workState), JsonUtils.toJson(rule));
    }

    private void publishConfig(String appName, ThreadPoolWorkState workState, AlarmExpansionRuleDTO rule) {
        ThreadPoolBaseInfo threadPoolBaseInfo = new ThreadPoolBaseInfo();
        ExpansionRuleDTO expansionRule = rule.getExpansionRule();
        threadPoolBaseInfo.setCorePoolSize(expansionRule.getCoreSize());
        threadPoolBaseInfo.setMaximumPoolSize(expansionRule.getMaxSize());
        threadPoolBaseInfo.setQueueCapacity(expansionRule.getQueueSize());
        threadPoolBaseInfo.setPoolName(workState.getPoolName());
        configService.publishConfig(appName, threadPoolBaseInfo);
    }

    private double divide(Integer num1, Integer num2) {

        return Double.parseDouble(num1 + "")
                / Double.parseDouble(num2 + "");
    }


    @Getter
    public static class CollectionReportEvent extends ApplicationEvent {

        private ThreadPoolConfigReportBaseInfo baseInfo;

        public CollectionReportEvent(Object source, ThreadPoolConfigReportBaseInfo baseInfo) {
            super(source);
            this.baseInfo = baseInfo;
        }
    }
}