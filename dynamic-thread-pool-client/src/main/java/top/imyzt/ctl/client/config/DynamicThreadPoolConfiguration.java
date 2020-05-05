package top.imyzt.ctl.client.config;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import top.imyzt.ctl.client.core.executor.DynamicThreadPoolTaskExecutor;
import top.imyzt.ctl.common.pojo.dto.PoolConfigDTO;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author imyzt
 * @date 2020/05/04
 * @description 动态线程池管理配置中心
 */
@Component
public class DynamicThreadPoolConfiguration {

    private static final Logger log = LoggerFactory.getLogger(DynamicThreadPoolConfiguration.class);
    private final ApplicationContext applicationContext;

    @Value("${spring.dynamic.server-url}")
    private String serverUrl;

    private Map<String, DynamicThreadPoolTaskExecutor> dynamicThreadPoolTaskExecutorMap;

    public DynamicThreadPoolConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Map<String, DynamicThreadPoolTaskExecutor> getDynamicThreadPoolTaskExecutorMap() {
        return dynamicThreadPoolTaskExecutorMap;
    }

    /**
     * 线程配置初始化到组件
     */
    @PostConstruct
    public void initConfigThreadPool() {

        // 获得所有线程池对象
        Map<String, DynamicThreadPoolTaskExecutor> dynamicThreadPoolMap =
                applicationContext.getBeansOfType(DynamicThreadPoolTaskExecutor.class);

        log.info("配置线程池数量, size={}", dynamicThreadPoolMap.size());

        this.dynamicThreadPoolTaskExecutorMap = dynamicThreadPoolMap;
    }

    /**
     * 配置信息上报
     */
    @PostConstruct
    public void configReport() {

        ArrayList<PoolConfigDTO> poolConfigList = new ArrayList<>();

        dynamicThreadPoolTaskExecutorMap.forEach((tName, executor) -> {

            ThreadPoolExecutor threadPoolExecutor = executor.getThreadPoolExecutor();

            PoolConfigDTO config = new PoolConfigDTO();

            config.setCorePoolSize(executor.getCorePoolSize());
            config.setMaximumPoolSize(executor.getMaxPoolSize());
            config.setPoolName(tName);
            config.setQueueCapacity(executor.getKeepAliveSeconds());
            config.setKeepAliveSeconds(executor.getKeepAliveSeconds());

            config.setQueueType(threadPoolExecutor.getQueue().getClass().getSimpleName());

            poolConfigList.add(config);

        });

        HttpResponse response = HttpRequest.post(serverUrl + "/config/init")
                .body(JSON.toJSONString(poolConfigList))
                .contentType("application/json")
                .execute();

        String currNewVersion = response.body();

        log.info("初始化信息上报完成, 服务器端返回最新版本={}", currNewVersion);
    }
}