package top.imyzt.ctl.server.pojo.entity;

import lombok.Data;

/**
 * @author imyzt
 * @date 2020/06/28
 * @description 扩容规则
 */
@Data
public class ExpansionRule {

    private Integer id;

    /**
     * 绑定一个报警规则
     * 达到报警规则, 则自动扩容
     * 发报警通知, 然后扩容成功发扩容通知
     */
    private Integer alarmRuleId;

    /**
     * 扩容后核心线程数
     */
    private Integer coreSize;

    /**
     * 扩容后最大线程数
     */
    private Integer maxSize;

    /**
     * 扩容后队列大小
     */
    private Integer queueSize;
}