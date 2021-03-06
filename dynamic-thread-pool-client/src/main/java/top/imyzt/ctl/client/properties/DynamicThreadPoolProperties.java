package top.imyzt.ctl.client.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * @author imyzt
 * @date 2020/05/04
 * @description 动态线程池配置
 */
@Validated
@ConfigurationProperties(prefix = "spring.dynamic-thread-pool")
@Data
public class DynamicThreadPoolProperties {

    /**
     * 服务器上报地址
     */
    private String serverUrl;

    /**
     * 数据上报周期
     * 单位=毫秒
     */
    private long fixedRate = 2000;

}