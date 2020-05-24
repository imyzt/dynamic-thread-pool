package top.imyzt.ctl.server.core.watch;

import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author imyzt
 * @date 2020/05/24
 * @description 配置变更监视器
 */
public interface ConfigChangeWatch {

    /**
     * 服务变化监听
     * @param appName 实例名称
     * @return 异步结果类
     */
    DeferredResult<String> watch(String appName);

    /**
     * 发布配置
     * 不承载真正的配置发布工作, 仅完成通知客户端的目的
     * @param appName 实例名称
     * @param poolName 线程池名称
     */
    void publishConfig(String appName, String poolName);
}
