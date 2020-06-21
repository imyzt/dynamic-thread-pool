package top.imyzt.ctl.common.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.imyzt.ctl.common.pojo.dto.info.ThreadPoolWorkState;

/**
 * @author imyzt
 * @date 2020/05/04
 * @description 线程池配置定时上报信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ThreadPoolConfigReportInfo extends ThreadPoolConfigReportBaseInfo<ThreadPoolWorkState> {

    /**
     * 应用端口
     */
    private Integer appPort;

    /**
     * 实例名称
     */
    private String instantName;

    /**
     * ip 地址
     */
    private String ipAddress;

    /**
     * 实例当前配置版本
     */
    private Integer currConfigVersion;

}