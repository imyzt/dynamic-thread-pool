package top.imyzt.ctl.common.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @author imyzt
 * @date 2020/05/05
 * @description 线程池配置
 */
@Data
public class PoolConfigDTO {

    private String appName;

    private List<ThreadPoolBaseInfo> threadPoolConfigList;

}