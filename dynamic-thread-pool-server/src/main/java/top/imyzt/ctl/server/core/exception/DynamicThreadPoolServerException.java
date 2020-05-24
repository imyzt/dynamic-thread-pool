package top.imyzt.ctl.server.core.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author imyzt
 * @date 2020/05/24
 * @description 动态线程池服务端异常
 */
@Getter
@Setter
public class DynamicThreadPoolServerException extends RuntimeException {

    private int code;
    private String msg;

    public static DynamicThreadPoolServerException error(String errorMsg) {
        DynamicThreadPoolServerException exception = new DynamicThreadPoolServerException();
        exception.setCode(500500);
        exception.setMsg(errorMsg);
        return exception;
    }

    public static DynamicThreadPoolServerException toast(String toastMsg) {
        DynamicThreadPoolServerException exception = new DynamicThreadPoolServerException();
        exception.setCode(500100);
        exception.setMsg(toastMsg);
        return exception;
    }
}