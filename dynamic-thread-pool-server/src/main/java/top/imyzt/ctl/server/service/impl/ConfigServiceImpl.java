package top.imyzt.ctl.server.service.impl;

import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import top.imyzt.ctl.common.pojo.dto.PoolConfigDTO;
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

    @Override
    public void saveClientConfig(List<PoolConfigDTO> dtoList) {

        for (PoolConfigDTO dto : dtoList) {

            Criteria criteria = Criteria.where("appName").is(dto.getAppName())
                    .and("poolName").is(dto.getPoolName());
            Update update = new Update().set("corePoolSize", dto.getCorePoolSize())
                    .set("maximumPoolSize", dto.getMaximumPoolSize())
                    .set("queueType", dto.getQueueType())
                    .set("queueCapacity", dto.getQueueCapacity());

            UpdateResult upsert = mongoTemplate.upsert(new Query(criteria), update, ThreadPoolConfig.class);

            log.info("upsert={}", upsert);

        }

    }
}