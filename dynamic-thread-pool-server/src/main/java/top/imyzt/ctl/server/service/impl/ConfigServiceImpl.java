package top.imyzt.ctl.server.service.impl;

import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import top.imyzt.ctl.common.pojo.dto.info.ThreadPoolBaseInfo;
import top.imyzt.ctl.common.pojo.dto.ThreadPoolConfigReportBaseInfo;
import top.imyzt.ctl.common.utils.JsonUtils;
import top.imyzt.ctl.server.core.watch.ConfigChangeWatch;
import top.imyzt.ctl.server.listener.PoolReportListener;
import top.imyzt.ctl.server.service.ConfigService;
import top.imyzt.ctl.server.utils.RequestUtils;

import javax.annotation.Resource;
import java.util.List;

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
    @Resource
    private ApplicationContext applicationContext;

    @Override
    public void saveClientConfig(ThreadPoolConfigReportBaseInfo config) {

        // TODO 可优化
        // 将对象MD5之后, 将config对象增加存储version字段, 值为MD5, 同一个应用程序不同实例,每次上报时将内容md5, 对比数据库md5, 避免多次存储数据库相同数据

        String appName = config.getAppName();

        List<ThreadPoolBaseInfo> threadPoolConfigList = config.getThreadPoolConfigList();
        threadPoolConfigList.forEach(threadPoolConfig -> this.saveOrUpdateThreadPoolConfig(appName, threadPoolConfig));

        log.info("收到初始化上报配置信息, {}", JsonUtils.toJsonString(config));
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

    @Override
    public void saveThreadPoolWorkerState(ThreadPoolConfigReportBaseInfo dto) {

        // 保存工作状态
        mongoTemplate.save(dto);

        log.info("收到采集上报数据, appName={}, {}", dto.getAppName(), top.imyzt.ctl.common.utils.JsonUtils.toJsonString(dto));

        // 放入监听
        applicationContext.publishEvent(new PoolReportListener.CollectionReportEvent(this, dto));
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