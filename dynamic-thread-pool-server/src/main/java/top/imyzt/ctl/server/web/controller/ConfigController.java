package top.imyzt.ctl.server.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import top.imyzt.ctl.common.pojo.dto.PoolConfigDTO;
import top.imyzt.ctl.common.pojo.dto.ThreadPoolConfigReportBaseInfo;
import top.imyzt.ctl.server.service.ConfigService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author imyzt
 * @date 2020/05/05
 * @description 获取最新配置信息
 */
@Slf4j
@RestController
@RequestMapping("config")
@CrossOrigin("*")
public class ConfigController {

    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private ConfigService configService;

    /**
     * 1. 获取最新的线程配置
     * 2. 接收第一次启动时的配置信息上报, 用于确定总共有多少个线程池已被接管,
     *      一个线程池在多实例部署时,会出现N个, 使用spring.application.name + threadPoolBeanName 确定一个唯一的线程池
     * 3. 维护长连接, 通知客户端配置更新
     */

    @PostMapping("/init")
    public String initConfig(@RequestBody ThreadPoolConfigReportBaseInfo dto) throws JsonProcessingException {

        log.info("收到初始化上报配置信息, {}", objectMapper.writeValueAsString(dto));

        configService.saveClientConfig(dto);

        return "1";
    }

    /**
     * 监听配置改变
     *
     * 理论上一个应用实例只需要监听一次就行, 后续在界面上更新配置时, 更新了哪一个线程池也能定位到是哪一个的应用
     */
    @GetMapping("/chance_monitor")
    public DeferredResult<Object> chanceMonitor(String appName) {



        return configService.configChanceMonitor(appName);
    }


}