package top.imyzt.ctl.server.core.watch.impl;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;
import top.imyzt.ctl.server.core.watch.ConfigChangeWatch;

/**
 * @author imyzt
 * @date 2020/05/24
 * @description guava实现的配置保存, 单机, 不支持分布式
 */
@Component
@Slf4j
public class GuavaConfigChangeWatch implements ConfigChangeWatch {

    /**
     * guava中的Multimap，多值map,对map的增强，一个key可以保持多个value
     *
     * 一个appName会有N个实际实例
     */
    private Multimap<String, DeferredResult<String>> watchRequests = Multimaps.synchronizedSetMultimap(HashMultimap.create());

    @Override
    public DeferredResult<String> watch(String appName) {

        DeferredResult<String> deferredResult = new DeferredResult<>();
        // 当deferredResult完成时（不论是超时还是异常还是正常完成），移除watchRequests中相应的watch key
        deferredResult.onCompletion(() -> {
            log.info("remove key={}", appName);
            watchRequests.remove(appName, deferredResult);
        });
        watchRequests.put(appName, deferredResult);

        return deferredResult;
    }

    @Override
    public void publishConfig(String appName, String poolName) {
        if (watchRequests.containsKey(appName)) {
            // 通知所有watch这个namespace变更的长轮训配置变更结果
            watchRequests.get(appName)
                    .forEach(deferredResult -> deferredResult.setResult(poolName));
        }
    }
}