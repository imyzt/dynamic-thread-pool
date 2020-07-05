package top.imyzt.ctl.server.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * @author imyzt
 * @date 2020/05/24
 * @description json 工具类
 */
@Component
public class JsonUtils {

    private static ObjectMapper objectMapper;

    @Autowired
    public JsonUtils(ObjectMapper objectMapper) {
        JsonUtils.objectMapper = objectMapper;
    }

    public static String toJson(Object value) {
        if (Objects.isNull(value)) {
            return "null";
        }
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    public static <T> Optional<T> fromJson(String jsonStr, Class<T> clazz) {
        T data;
        try {
            data = objectMapper.readValue(jsonStr, clazz);
        } catch (JsonProcessingException e) {
            data = null;
        }

        return Optional.ofNullable(data);
    }
}