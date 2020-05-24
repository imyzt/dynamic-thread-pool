package top.imyzt.ctl.common.pojo.vo;

import lombok.Data;

import static top.imyzt.ctl.common.constants.ResponseConstants.*;

/**
 * @author imyzt
 * @date 2020/05/24
 * @description 响应体
 */
@Data
public class Result<T> {

    private int code;
    private String msg;
    private T data;

    public boolean isOk() {
        return SUCCESS == code;
    }

    public static <T> Result<T> ok (String msg) {
        Result<T> tResult = new Result<T>();
        tResult.setCode(SUCCESS);
        tResult.setMsg(msg);
        return tResult;
    }

    public static <T> Result<T> ok (T data) {
        Result<T> tResult = new Result<T>();
        tResult.setCode(SUCCESS);
        tResult.setMsg("操作成功");
        tResult.setData(data);
        return tResult;
    }

    public static <T> Result<T> fail (int code, String msg) {
        Result<T> tResult = new Result<T>();
        tResult.setCode(code);
        tResult.setMsg(msg);
        return tResult;
    }

    public static <T> Result<T> toast (String msg) {
        Result<T> tResult = new Result<T>();
        tResult.setCode(TOAST);
        tResult.setMsg(msg);
        return tResult;
    }

    public static <T> Result<T> error (String msg) {
        Result<T> tResult = new Result<T>();
        tResult.setCode(ERROR);
        tResult.setMsg(msg);
        return tResult;
    }
}