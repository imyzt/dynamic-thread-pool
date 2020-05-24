package top.imyzt.ctl.server.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import top.imyzt.ctl.common.pojo.vo.Result;

import static top.imyzt.ctl.common.constants.ResponseConstants.TIME_OUT;

/**
 * @author imyzt
 * @date 2020/05/24
 * @description 描述信息
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 请求超时
     */
    @ResponseStatus(HttpStatus.NOT_MODIFIED)
    @ExceptionHandler(value = AsyncRequestTimeoutException.class)
    public Result<String> timeoutException() {
        return Result.fail(TIME_OUT, "请求超时");
    }
}