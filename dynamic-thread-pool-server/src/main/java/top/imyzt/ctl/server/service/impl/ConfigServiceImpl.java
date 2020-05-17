package top.imyzt.ctl.server.service.impl;

import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import top.imyzt.ctl.common.pojo.dto.ThreadPoolBaseInfo;
import top.imyzt.ctl.common.pojo.dto.ThreadPoolConfigReportBaseInfo;
import top.imyzt.ctl.server.pojo.entity.ThreadPoolConfig;
import top.imyzt.ctl.server.service.ConfigService;

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

    @Resource
    private MongoTemplate mongoTemplate;
    @Resource(name = "threadPoolConfigChangeMonitor")
    private ThreadPoolTaskExecutor threadPoolConfigChangeMonitor;


    @Override
    public void saveClientConfig(ThreadPoolConfigReportBaseInfo threadPoolConfigReportBaseInfo) {

        String appName = threadPoolConfigReportBaseInfo.getAppName();

        List<ThreadPoolBaseInfo> threadPoolInfo = threadPoolConfigReportBaseInfo.getThreadPoolInfo();

        for (ThreadPoolBaseInfo dto : threadPoolInfo) {

            Criteria criteria = Criteria.where("appName").is(appName)
                    .and("poolName").is(dto.getPoolName());
            Update update = new Update().set("corePoolSize", dto.getCorePoolSize())
                    .set("maximumPoolSize", dto.getMaximumPoolSize())
                    .set("queueType", dto.getQueueType())
                    .set("queueCapacity", dto.getQueueCapacity());

            UpdateResult upsert = mongoTemplate.upsert(new Query(criteria), update, ThreadPoolConfig.class);

            log.info("upsert={}", upsert);

        }

    }

    @Override
    public DeferredResult<Object>   configChanceMonitor(String appName) {
        DeferredResult<Object> response = new DeferredResult<>(
                // 请求的超时时间(10秒)
                10000L,
                // 超时后响应的结果
                "retry");

        /*response.onCompletion(() -> {
            // 请求处理完成后所做的一些工作
        });*/

        threadPoolConfigChangeMonitor.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 调用此方法时立即向浏览器发出响应；未调用时请求被挂起
            response.setResult(new Object());
            log.info("返回给用户消息");
        });


        return response;
    }
}