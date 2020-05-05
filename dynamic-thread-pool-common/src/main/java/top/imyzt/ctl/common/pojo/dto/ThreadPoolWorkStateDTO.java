package top.imyzt.ctl.common.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author imyzt
 * @date 2020/05/04
 * @description 线程池工作状态信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThreadPoolWorkStateDTO {

    private String poolName;

    private Integer corePoolSize;

    private Integer maximumPoolSize;

    private Integer poolSize;

    private Integer keepAliveSeconds;

    private Integer activeCount;

    private String queueType;

    private Integer queueCapacity;

    private Integer queueSize;

    private Integer queueRemainingCapacity;

    private Long completedTaskCount;

    private Integer largestPoolSize;

    private Integer rejectCount;
}