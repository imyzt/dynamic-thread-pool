package top.imyzt.ctl.server.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import top.imyzt.ctl.common.pojo.dto.DynamicThreadPoolReportDTO;
import top.imyzt.ctl.server.dao.repository.ThreadPoolWorkerStateRepository;

/**
 * @author imyzt
 * @date 2020/05/05
 * @description 数据采集
 */
@Slf4j
@RestController
@RequestMapping("collection")
public class CollectionController {

    @Autowired
    private ThreadPoolWorkerStateRepository threadPoolWorkerStateRepository;

    @PostMapping
    public Mono<DynamicThreadPoolReportDTO> collection (@RequestBody DynamicThreadPoolReportDTO dto) {

        log.info("收到采集上报数据, {}", dto.toString());

        return threadPoolWorkerStateRepository.save(dto);
    }
}