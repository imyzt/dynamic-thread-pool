package top.imyzt.ctl.common.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @author imyzt
 * @date 2020/05/04
 * @description 动态线程池上报信息
 */
@Data
public class DynamicThreadPoolReportDTO {

    /**
     * 应用名称
     */
    private String appName;

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

    /**
     * 线程池工作信息
     */
    private List<ThreadPoolWorkStateDTO> workStateList;
}