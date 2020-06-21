package top.imyzt.ctl.common.pojo.dto.info;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author imyzt
 * @date 2020/05/04
 * @description 线程池工作状态信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ThreadPoolWorkState extends ThreadPoolBaseInfo {

    private Integer poolSize;
    private Integer queueSize;

    private Integer activeCount;
    private Integer queueRemainingCapacity;
    private Long completedTaskCount;
    private Integer largestPoolSize;
    private Long taskCount;

    private Integer rejectCount;
}