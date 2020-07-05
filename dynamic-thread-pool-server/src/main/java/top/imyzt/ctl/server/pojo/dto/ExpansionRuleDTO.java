package top.imyzt.ctl.server.pojo.dto;

import lombok.Data;

/**
 * @author imyzt
 * @date 2020/07/05
 * @description 报警规则
 */
@Data
public class ExpansionRuleDTO {

    private Integer id;

    /**
     * 绑定一个报警规则
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