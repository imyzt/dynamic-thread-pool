package top.imyzt.ctl.server.utils;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;

/**
 * @author imyzt
 * @date 2020/05/24
 * @description md5工具类
 */
@UtilityClass
public class Md5Utils {

    private static HashFunction hf = Hashing.md5();

    public static String md5(String data) {
        HashCode hash = hf.newHasher().putString(data, StandardCharsets.UTF_8).hash();
        return hash.toString();
    }
}