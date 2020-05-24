package top.imyzt.ctl.server.service.impl;

import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import top.imyzt.ctl.common.pojo.dto.ThreadPoolBaseInfo;
import top.imyzt.ctl.common.pojo.dto.ThreadPoolConfigReportBaseInfo;
import top.imyzt.ctl.server.core.watch.ConfigChangeWatch;
import top.imyzt.ctl.server.service.ConfigService;
import top.imyzt.ctl.server.utils.JsonUtils;
import top.imyzt.ctl.server.utils.RequestUtils;

import javax.annotation.Resource;

/**
 * @author imyzt
 * @date 2020/05/05
 * @description 配置服务
 */
@Service
@Slf4j
public class ConfigServiceImpl implements ConfigService {

    @Resource(name = "guavaConfigChangeWatch")
    private ConfigChangeWatch configChangeWatch;
    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void saveClientConfig(ThreadPoolConfigReportBaseInfo config) {

        String appName = config.getAppName();

        config.getThreadPoolConfigList()
                .forEach(threadPoolConfig -> this.saveOrUpdateThreadPoolConfig(appName, threadPoolConfig));

        log.info("收到初始化上报配置信息, {}", JsonUtils.toJson(config));
    }

    @Override
    public DeferredResult<String> configChanceMonitor(String appName) {

        DeferredResult<String> watch = configChangeWatch.watch(appName);

        String clientIpAddr = RequestUtils.getClientIpAddr();

        log.info("客户端发起长连接监听, appName={}, clientIpAddr={}", appName, clientIpAddr);

        return watch;
    }

    @Override
    public void publishConfig(String appName, ThreadPoolBaseInfo config) {

        // 持久化配置到数据库, 备于下一次查看 & 通知客户端之后客户端拉取新配置
        this.saveOrUpdateThreadPoolConfig(appName, config);

        // 发布配置, 通知客户端拉取
        configChangeWatch.publishConfig(appName, config.getPoolName());

        log.info("发布新配置, appName={}, poolName={}", appName, config.getPoolName());
    }

    @Override
    public ThreadPoolBaseInfo getNewConfig(String appName, String poolName) {

        Criteria criteria = Criteria.where("appName").is(appName)
                .and("poolName").is(poolName);

        return mongoTemplate.findOne(new Query(criteria), ThreadPoolBaseInfo.class);
    }

    private void saveOrUpdateThreadPoolConfig(String appName, ThreadPoolBaseInfo threadPoolConfig) {

        Criteria criteria = Criteria.where("appName").is(appName)
                .and("poolName").is(threadPoolConfig.getPoolName());
        Update update = new Update()
                .set("threadNamePrefix", threadPoolConfig.getThreadNamePrefix())
                .set("corePoolSize", threadPoolConfig.getCorePoolSize())
                .set("maximumPoolSize", threadPoolConfig.getMaximumPoolSize())
                .set("queueType", threadPoolConfig.getQueueType())
                .set("queueCapacity", threadPoolConfig.getQueueCapacity())
                .set("keepAliveSeconds", threadPoolConfig.getKeepAliveSeconds());

        UpdateResult updateResult = mongoTemplate.upsert(new Query(criteria), update, ThreadPoolBaseInfo.class);

        log.info("updateResult={}", updateResult);
    }
}