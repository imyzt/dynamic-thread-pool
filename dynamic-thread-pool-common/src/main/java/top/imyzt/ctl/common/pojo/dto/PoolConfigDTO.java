package top.imyzt.ctl.common.pojo.dto;

import lombok.Data;

/**
 * @author imyzt
 * @date 2020/05/05
 * @description 线程池配置
 */
@Data
public class PoolConfigDTO {

    private String poolName;

    private Integer corePoolSize;

    private Integer maximumPoolSize;

    private String queueType;

    private Integer queueCapacity;

    private Integer keepAliveSeconds;

}