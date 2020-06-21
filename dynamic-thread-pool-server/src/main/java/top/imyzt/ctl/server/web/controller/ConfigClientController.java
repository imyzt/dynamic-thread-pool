package top.imyzt.ctl.server.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import top.imyzt.ctl.common.constants.ServerEndpoint;
import top.imyzt.ctl.common.pojo.dto.info.ThreadPoolBaseInfo;
import top.imyzt.ctl.common.pojo.dto.ThreadPoolConfigReportBaseInfo;
import top.imyzt.ctl.common.pojo.dto.info.ThreadPoolWorkState;
import top.imyzt.ctl.server.service.ConfigService;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author imyzt
 * @date 2020/05/05
 * @description 客户端 获取/上报 配置信息
 */
@Slf4j
@RestController
@CrossOrigin("*")
public class ConfigClientController {

    @Resource
    private ConfigService configService;

    /**
     * 1. 获取最新的线程配置
     * 2. 接收第一次启动时的配置信息上报, 用于确定总共有多少个线程池已被接管,
     *      一个线程池在多实例部署时,会出现N个, 使用spring.application.name + threadPoolBeanName 确定一个唯一的线程池
     * 3. 维护长连接, 通知客户端配置更新
     */
    @RequestMapping(method = RequestMethod.POST, value = ServerEndpoint.INIT)
    public void initConfig(@Validated @RequestBody ThreadPoolConfigReportBaseInfo dto) {

        configService.saveClientConfig(dto);
    }

    /**
     * 采集定时上报线程池的工作状态信息
     */
    @RequestMapping(method = RequestMethod.POST, value = ServerEndpoint.WORKER_STATE)
    public void workerState (@RequestBody ThreadPoolConfigReportBaseInfo<ThreadPoolWorkState> dto) {

        configService.saveThreadPoolWorkerState(dto);
    }

    /**
     * 监听配置改变
     *
     * 理论上一个应用实例只需要监听一次就行, 后续在界面上更新配置时, 更新了哪一个线程池也能定位到是哪一个的应用
     */
    @RequestMapping(method = RequestMethod.GET, value = ServerEndpoint.WATCH)
    public DeferredResult<String> chanceMonitor(@Validated @PathVariable @NotBlank(message = "接入名称不可为空") String appName) {

        return configService.configChanceMonitor(appName);
    }

    /**
     * 获取最新配置
     */
    @RequestMapping(method = RequestMethod.GET, value = ServerEndpoint.GET_NEW_CONFIG)
    public ThreadPoolBaseInfo getNewConfig(@PathVariable String appName, @PathVariable String poolName) {

        return configService.getNewConfig(appName, poolName);
    }


}