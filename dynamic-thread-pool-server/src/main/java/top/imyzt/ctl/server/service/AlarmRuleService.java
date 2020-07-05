package top.imyzt.ctl.server.service;

import top.imyzt.ctl.server.pojo.dto.AlarmExpansionRuleDTO;
import top.imyzt.ctl.server.pojo.entity.AlarmRule;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 报警规则表 服务类
 * </p>
 *
 * @author imyzt
 * @date 2020-07-05
 */
public interface AlarmRuleService extends IService<AlarmRule> {

    /**
     * 获取报警规则列表
     *
     * @param appName 应用名称
     * @param poolName 线程名称
     * @return 报警规则
     */
    List<AlarmExpansionRuleDTO> getRuleList(String appName, String poolName);
}
