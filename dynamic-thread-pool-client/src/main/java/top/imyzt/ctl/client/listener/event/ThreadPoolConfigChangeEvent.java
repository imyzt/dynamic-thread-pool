package top.imyzt.ctl.client.listener.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author imyzt
 * @date 2020/06/07
 * @description 线程池配置改变事件
 */
@Getter
public class ThreadPoolConfigChangeEvent extends ApplicationEvent {

    private String changePoolName;

    public ThreadPoolConfigChangeEvent(Object source, String changePoolName) {
        super(source);
        this.changePoolName = changePoolName;
    }
}