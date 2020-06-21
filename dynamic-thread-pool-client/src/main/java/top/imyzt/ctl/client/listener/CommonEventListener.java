package top.imyzt.ctl.client.listener;

import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import top.imyzt.ctl.client.listener.event.ThreadPoolConfigChangeEvent;
import top.imyzt.ctl.client.utils.HttpUtils;
import top.imyzt.ctl.client.utils.ThreadPoolUtils;
import top.imyzt.ctl.common.pojo.dto.info.ThreadPoolBaseInfo;

import java.util.HashMap;

import static top.imyzt.ctl.common.constants.ServerEndpoint.GET_NEW_CONFIG;

/**
 * @author imyzt
 * @date 2020/06/07
 * @description 线程池配置变更监听器
 */
@Component
@Slf4j
public class CommonEventListener {

    @Value("${spring.application.name}")
    private String appName;
    @Value("${spring.dynamic-thread-pool.server-url}")
    private String serverUrl;

    /**
     * 修改线程池配置
     */
    @Async("configChange")
    @EventListener
    public void changeConfig (ThreadPoolConfigChangeEvent listener) {

        String poolName = listener.getChangePoolName();

        log.info("监听到配置改变, poolName={}", poolName);

        // 请求线程配置
        HashMap<String, String> param = Maps.newHashMap();
        param.put("appName", appName);
        param.put("poolName", poolName);
        HttpResponse httpResponse = HttpUtils.sendRestGet(serverUrl + GET_NEW_CONFIG, param);
        ThreadPoolBaseInfo info = JSON.parseObject(httpResponse.body(), ThreadPoolBaseInfo.class);

        log.info("拉取到最新配置, poolName={}, info={}", poolName, httpResponse.body());

        // 修改配置
        ThreadPoolUtils.editThreadPoolStatus(info);
    }
}