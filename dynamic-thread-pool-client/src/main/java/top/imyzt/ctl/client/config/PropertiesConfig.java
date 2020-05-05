package top.imyzt.ctl.client.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import top.imyzt.ctl.client.properties.ClientProperties;

/**
 * @author imyzt
 * @date 2020/05/04
 * @description 开启properties配置
 */
@Configuration
@EnableConfigurationProperties(value = {
        ClientProperties.class
})
public class PropertiesConfig {
}