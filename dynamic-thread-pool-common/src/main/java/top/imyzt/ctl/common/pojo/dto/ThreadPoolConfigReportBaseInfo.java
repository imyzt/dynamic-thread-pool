package top.imyzt.ctl.common.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.imyzt.ctl.common.pojo.dto.info.ThreadPoolBaseInfo;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author imyzt
 * @date 2020/05/04
 * @description 线程池配置上报基础信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThreadPoolConfigReportBaseInfo<T extends ThreadPoolBaseInfo> {

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 线程池信息
     */
    @NotNull(message = "线程池配置信息不能为空")
    private List<T> threadPoolConfigList;

}