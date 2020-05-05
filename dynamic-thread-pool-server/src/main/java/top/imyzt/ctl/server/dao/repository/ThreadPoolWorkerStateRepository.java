package top.imyzt.ctl.server.dao.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import top.imyzt.ctl.common.pojo.dto.DynamicThreadPoolReportDTO;

/**
 * @author imyzt
 * @date 2020/05/05
 * @description 线程池工作状态信息
 */
@Repository
public interface ThreadPoolWorkerStateRepository extends ReactiveMongoRepository<DynamicThreadPoolReportDTO, Long> {
}