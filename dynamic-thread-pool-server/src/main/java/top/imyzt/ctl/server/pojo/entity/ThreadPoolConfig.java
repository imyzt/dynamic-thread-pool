package top.imyzt.ctl.server.pojo.entity;

import lombok.Data;

/**
 * @author imyzt
 * @date 2020/05/05
 * @description 线程池配置信息
 */
@Data
public class ThreadPoolConfig {



    private String appName;

    private String poolName;

    private Integer corePoolSize;

    private Integer maximumPoolSize;

    private String queueType;

    private Integer queueCapacity;

    private Integer keepAliveSeconds;
}