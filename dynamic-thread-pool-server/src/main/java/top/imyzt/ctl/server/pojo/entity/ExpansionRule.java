package top.imyzt.ctl.server.pojo.entity;

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
 * 扩容规则
 * </p>
 *
 * @author imyzt
 * @date 2020-07-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("expansion_rule")
public class ExpansionRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
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

    /**
     * 逻辑删除
     */
    private Boolean delFlag;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;


}
