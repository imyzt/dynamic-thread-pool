package top.imyzt.ctl.server.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imyzt.ctl.common.pojo.dto.ThreadPoolConfigReportBaseInfo;

import javax.annotation.Resource;

/**
 * @author imyzt
 * @date 2020/05/05
 * @description 数据采集
 */
@Slf4j
@RestController
@RequestMapping("collection")
public class CollectionController {

    @Resource
    private MongoTemplate mongoTemplate;

    @PostMapping
    public String collection(@RequestBody ThreadPoolConfigReportBaseInfo dto) {

        log.info("收到采集上报数据, {}", dto.toString());

        mongoTemplate.save(dto);

        return "1";
    }
}