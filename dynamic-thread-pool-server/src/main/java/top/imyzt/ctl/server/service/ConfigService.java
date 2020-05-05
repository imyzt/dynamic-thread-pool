package top.imyzt.ctl.server.service;

import top.imyzt.ctl.common.pojo.dto.PoolConfigDTO;

import java.util.List;

/**
 * @author imyzt
 * @date 2020/05/05
 * @description 配置服务
 */
public interface ConfigService {

    /**
     * 保存接入客户端的基础配置信息, 客户端启动时, 会主动上报
     * @param dtoList 配置列表
     */
    void saveClientConfig(List<PoolConfigDTO> dtoList);
}