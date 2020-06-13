package top.imyzt.ctl.client.clientsimple.web.controller;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imyzt.ctl.client.utils.ThreadPoolUtils;
import top.imyzt.ctl.common.pojo.dto.ThreadPoolBaseInfo;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author imyzt
 * @date 2020/05/17
 * @description 描述信息
 */
@RestController
public class TestController {

    @Resource
    private ThreadPoolTaskExecutor printThread;

    @GetMapping("test")
    public void test(Integer timeout, Integer num) {
        for (int i = 0; i < num; i++) {
            printThread.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(timeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @GetMapping("edit")
    public void test() {
        ThreadPoolBaseInfo config = new ThreadPoolBaseInfo();
        config.setCorePoolSize(20);
        config.setQueueCapacity(100);
        config.setMaximumPoolSize(20);
        config.setPoolName("printThread");
        ThreadPoolUtils.editThreadPoolStatus(config);
    }
}