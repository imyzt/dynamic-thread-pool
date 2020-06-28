package top.imyzt.ctl.client.common;

import java.lang.annotation.*;

/**
 * @author imyzt
 * @date 2020/06/27
 * @description 需要处理的动态线程池
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DynamicThreadPool {

}