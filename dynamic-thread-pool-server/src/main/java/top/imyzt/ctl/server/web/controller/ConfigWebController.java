package top.imyzt.ctl.server.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.imyzt.ctl.common.pojo.dto.ThreadPoolBaseInfo;
import top.imyzt.ctl.server.service.ConfigService;

import javax.annotation.Resource;

/**
 * @author imyzt
 * @date 2020/05/05
 * @description 服务端展示界面配置信息
 */
@Slf4j
@RestController
@RequestMapping("config/web")
@CrossOrigin("*")
public class ConfigWebController {

    @Resource
    private ConfigService configService;

    /*
     * 1. web界面后续客户配置监控报警等
     * 2. 可以对配置进行自动扩容等
     */

    /**
     * web 界面发布配置
     */
    @PostMapping("publishConfig/{appName}")
    public String publishConfig(@PathVariable String appName, ThreadPoolBaseInfo threadPoolConfig) {

        configService.publishConfig(appName, threadPoolConfig);

        return "success";
    }

}