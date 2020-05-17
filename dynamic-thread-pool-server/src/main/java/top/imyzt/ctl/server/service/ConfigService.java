package top.imyzt.ctl.server.service;

import org.springframework.web.context.request.async.DeferredResult;
import top.imyzt.ctl.common.pojo.dto.PoolConfigDTO;
import top.imyzt.ctl.common.pojo.dto.ThreadPoolConfigReportBaseInfo;

import java.util.List;

/**
 * @author imyzt
 * @date 2020/05/05
 * @description 配置服务
 */
public interface ConfigService {

    /**
     * 保存接入客户端的基础配置信息, 客户端启动时, 会主动上报
     * @param threadPoolConfigReportBaseInfo 客户端上报嘻嘻
     */
    void saveClientConfig(ThreadPoolConfigReportBaseInfo threadPoolConfigReportBaseInfo);

    DeferredResult<Object> configChanceMonitor(String appName);
}