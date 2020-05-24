package top.imyzt.ctl.common.constants;

import lombok.experimental.UtilityClass;

/**
 * @author imyzt
 * @date 2020/05/24
 * @description 服务端controller配置地址
 */
@UtilityClass
public class ServerEndpoint {

    /**
     * 配置类客户端端点
     */
    public static final String CONFIG_CLIENT = "/config/client";

    /**
     * 初始化上报配置地址
     * /init
     */
    public static final String INIT = CONFIG_CLIENT + "/init";

    /**
     * 定时上报线程池工作状态任务
     * /workerState
     */
    public static final String WORKER_STATE = CONFIG_CLIENT + "/workerState";

    /**
     * 监听配置修改长连接地址
     * /watch/{appName}
     */
    public static final String WATCH = CONFIG_CLIENT + "/watch/{appName}";

    /**
     * 获取最新配置地址
     * /getNewConfig/{appName}/{poolName}
     */
    public static final String GET_NEW_CONFIG = CONFIG_CLIENT + "/getNewConfig/{appName}/{poolName}";
}