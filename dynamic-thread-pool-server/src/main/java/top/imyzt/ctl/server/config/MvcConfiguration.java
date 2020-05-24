package top.imyzt.ctl.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author imyzt
 * @date 2020/05/24
 * @description web mvc 配置
 */
public class MvcConfiguration implements WebMvcConfigurer {

    @Value("${spring.mvc.async.request-timeout}")
    private long mvcAsyncRequestTimeout;

    @Resource(name = "mvcTaskExecutor")
    private ThreadPoolTaskExecutor mvcTaskExecutor;

    /**
     * 配置异步线程池最大超时时间以及异步处理线程池
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor(mvcTaskExecutor);
        configurer.setDefaultTimeout(mvcAsyncRequestTimeout);
    }
}