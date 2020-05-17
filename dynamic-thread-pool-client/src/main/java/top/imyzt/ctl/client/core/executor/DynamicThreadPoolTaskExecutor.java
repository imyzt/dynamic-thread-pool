package top.imyzt.ctl.client.core.executor;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import top.imyzt.ctl.client.core.queue.ResizeCapacityLinkedBlockingQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @author imyzt
 * @date 2020/05/04
 * @description 动态线程池
 */
public class DynamicThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

    @Override
    protected BlockingQueue<Runnable> createQueue(int queueCapacity) {
        if (queueCapacity > 0) {
            return new ResizeCapacityLinkedBlockingQueue<>(queueCapacity);
        }
        else {
            return new SynchronousQueue<>();
        }
    }

}
