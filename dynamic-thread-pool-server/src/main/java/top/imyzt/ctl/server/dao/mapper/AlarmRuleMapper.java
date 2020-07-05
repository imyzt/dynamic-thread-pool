package top.imyzt.ctl.server.dao.mapper;

import org.apache.ibatis.annotations.Param;
import top.imyzt.ctl.server.pojo.dto.AlarmExpansionRuleDTO;
import top.imyzt.ctl.server.pojo.entity.AlarmRule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 报警规则表 Mapper 接口
 * </p>
 *
 * @author imyzt
 * @date 2020-07-05
 */
public interface AlarmRuleMapper extends BaseMapper<AlarmRule> {

    /**
     * 获取报警/扩容规则
     * @param appName 应用名称
     * @param poolName 线程池名称
     * @return 规则列表
     */
    List<AlarmExpansionRuleDTO> getRuleList(@Param("appName") String appName, @Param("poolName") String poolName);
}
