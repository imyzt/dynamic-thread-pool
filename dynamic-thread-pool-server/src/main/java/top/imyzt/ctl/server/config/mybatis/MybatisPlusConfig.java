package top.imyzt.ctl.server.config.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author imyzt
 * @date 2020/07/05
 * @description mybatis-plus 配置信息
 */
@Configuration
@EnableTransactionManagement
@MapperScan("top.imyzt.ctl.server.dao.mapper")
public class MybatisPlusConfig {


}