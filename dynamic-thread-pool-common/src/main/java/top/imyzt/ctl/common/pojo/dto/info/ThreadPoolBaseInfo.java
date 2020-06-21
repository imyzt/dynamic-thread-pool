package top.imyzt.ctl.common.pojo.dto.info;

import lombok.Data;

/**
 * @author imyzt
 * @date 2020/05/17
 * @description 线程池基础信息
 */
@Data
public class ThreadPoolBaseInfo {

    /**
     * 线程池名称
     */
    private String poolName;

    /**
     * 线程名称前缀
     */
    private String threadNamePrefix;

    /**
     * 核心线程池大小
     */
    private Integer corePoolSize;

    /**
     * 最大线程池大小
     */
    private Integer maximumPoolSize;

    /**
     * 队列类型
     */
    private String queueType;

    /**
     * 队列长度
     */
    private Integer queueCapacity;

    /**
     * 关闭空闲线程时机
     */
    private Integer keepAliveSeconds;
}