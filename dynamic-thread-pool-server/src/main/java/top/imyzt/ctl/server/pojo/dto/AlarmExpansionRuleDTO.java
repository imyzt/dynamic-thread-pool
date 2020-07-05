package top.imyzt.ctl.server.pojo.dto;

import lombok.Data;

/**
 * @author imyzt
 * @date 2020/07/05
 * @description 报警/扩容规则
 */
@Data
public class AlarmExpansionRuleDTO {

    /**
     * id
     */
    private Integer id;

    /**
     * 报警名称
     */
    private String ruleName;

    /**
     * 线程池活跃度
     */
    private Double taskCompletion;

    /**
     * 队列使用情况
     */
    private Double queueUsage;

    /**
     * 报警内容(支持通配符匹配)
     */
    private String content;

    /**
     * 扩容规则
     * 一条报警规则只能绑定一条扩容规则
     */
    private ExpansionRuleDTO expansionRule;
}