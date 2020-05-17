package top.imyzt.ctl.common.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author imyzt
 * @date 2020/05/04
 * @description 线程池配置上报基础信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThreadPoolConfigReportBaseInfo {

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 线程池信息
     */
    private List<ThreadPoolBaseInfo> threadPoolInfo;

}