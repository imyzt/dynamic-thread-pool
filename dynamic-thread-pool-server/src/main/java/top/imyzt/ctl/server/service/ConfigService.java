package top.imyzt.ctl.server.service;

import org.springframework.web.context.request.async.DeferredResult;
import top.imyzt.ctl.common.pojo.dto.info.ThreadPoolBaseInfo;
import top.imyzt.ctl.common.pojo.dto.ThreadPoolConfigReportBaseInfo;

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

    /**
     * 客户端发起监听, 60s长连接, 期间无配置更新返回304, 有配置更新立即端口, 由客户端发起请求获取新配置
     * @param appName 客户端实例名称
     * @return  长连接对象
     */
    DeferredResult<String> configChanceMonitor(String appName);

    /**
     * 发布配置信息
     * @param appName 客户端实例名称
     * @param threadPoolConfig 新的线程配置信息
     * @return
     */
    void publishConfig(String appName, ThreadPoolBaseInfo threadPoolConfig);

    /**
     * 获取对应线程池最新配置
     * @param appName 客户端实例名称
     * @param poolName 线程池名称
     * @return 最新配置信息
     */
    ThreadPoolBaseInfo getNewConfig(String appName, String poolName);

    /**
     * 保存线程池的工作状态信息
     * @param dto 线程池工作状态
     */
    void saveThreadPoolWorkerState(ThreadPoolConfigReportBaseInfo dto);
}