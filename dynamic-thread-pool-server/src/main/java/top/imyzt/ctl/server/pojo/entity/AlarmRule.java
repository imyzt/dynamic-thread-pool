package top.imyzt.ctl.server.pojo.entity;

import lombok.Data;

/**
 * @author imyzt
 * @date 2020/06/26
 * @description 报警规则
 */
@Data
public class AlarmRule {

    private Integer id;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 线程池名称
     */
    private String poolName;

    /**
     * 线程池活跃度
     */
    private Integer taskCompletion;

    /**
     * 队列使用情况
     */
    private Integer queueUsage;
}