package top.imyzt.ctl.server.pojo.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 报警规则表
 * </p>
 *
 * @author imyzt
 * @date 2020-07-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("alarm_rule")
public class AlarmRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 报警名称
     */
    private String ruleName;

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
    private BigDecimal taskCompletion;

    /**
     * 队列使用情况
     */
    private Integer queueUsage;

    /**
     * 报警内容(支持通配符匹配)
     */
    private String content;

    /**
     * 逻辑删除
     */
    private Boolean delFlag;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

}
