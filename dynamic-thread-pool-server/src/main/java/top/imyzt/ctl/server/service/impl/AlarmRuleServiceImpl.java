package top.imyzt.ctl.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.imyzt.ctl.server.dao.mapper.AlarmRuleMapper;
import top.imyzt.ctl.server.pojo.dto.AlarmExpansionRuleDTO;
import top.imyzt.ctl.server.pojo.entity.AlarmRule;
import top.imyzt.ctl.server.service.AlarmRuleService;

import java.util.List;

/**
 * <p>
 * 报警规则表 服务实现类
 * </p>
 *
 * @author imyzt
 * @date 2020-07-05
 */
@Service
public class AlarmRuleServiceImpl extends ServiceImpl<AlarmRuleMapper, AlarmRule> implements AlarmRuleService {

    @Override
    public List<AlarmExpansionRuleDTO> getRuleList(String appName, String poolName) {
        return baseMapper.getRuleList(appName, poolName);
    }
}
