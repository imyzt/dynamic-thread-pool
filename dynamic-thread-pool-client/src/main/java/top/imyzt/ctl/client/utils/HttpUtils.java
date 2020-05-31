package top.imyzt.ctl.client.utils;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import lombok.experimental.UtilityClass;
import top.imyzt.ctl.common.utils.JsonUtils;

import java.util.Map;

/**
 * @author imyzt
 * @date 2020/05/31
 * @description HTTP请求工具
 */
@UtilityClass
public class HttpUtils {

    /**
     * GET 请求
     */
    public HttpResponse sendRestGet(String requestUrl, Map<String, String> formMap) {

        for (Map.Entry<String, String> entry : formMap.entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue();
            requestUrl = requestUrl.replace("{" + k + "}", v);
        }

        return HttpRequest.get(requestUrl).execute();
    }

    public <T> T getRestData(String requestUrl, Map<String, String> formMap) {
        HttpResponse httpResponse = sendRestGet(requestUrl, formMap);
        return convertResponse(httpResponse);
    }

    private  <T> T convertResponse(HttpResponse httpResponse) {

        if (!httpResponse.isOk()) {
            throw new HttpException("responseHttp status[" + httpResponse.getStatus() + "]not successful!");
        }

        return JsonUtils.toJavaBean(httpResponse.body());
    }
}