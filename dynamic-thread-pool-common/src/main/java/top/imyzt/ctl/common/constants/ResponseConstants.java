package top.imyzt.ctl.common.constants;

import lombok.experimental.UtilityClass;

/**
 * @author imyzt
 * @date 2020/05/24
 * @description 响应状态码
 */
@UtilityClass
public class ResponseConstants {

    /**
     * 成功
     */
    public static final int SUCCESS = 0;
    
    /**
     * 基本业务异常, 可以toast
     * 比如表单参数错误之类
     */
    public static final int TOAST = 500100;

    /**
     * 超时, 客户端可发起重试
     */
    public static final int TIME_OUT = 500304;

    /**
     * 未找到, 客户端可视内容决定是否toast
     */
    public static final int NOT_FOUND = 500400;

    /**
     * 系统异常, 未知原因
     */
    public static final int ERROR = 500500;
    
    
    
}