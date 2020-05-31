package top.imyzt.ctl.common.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.experimental.UtilityClass;

/**
 * @author imyzt
 * @date 2020/05/31
 * @description JSON 工具
 */
@UtilityClass
public class JsonUtils {

    private static final Gson GSON = new Gson();

    public <T> T toJavaBean(String json) {
        return GSON.fromJson(json, new TypeToken<T>(){}.getType());
    }

    public String toJsonString(Object javaBean) {
        return GSON.toJson(javaBean);
    }
}