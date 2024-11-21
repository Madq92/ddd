package cc.mikaka.ddd.common.sequence.impl.mysql.utils;

import lombok.experimental.UtilityClass;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/7/20
 * @since 1.0
 */
@UtilityClass
public class EnvUtil {

    public static String getStr(String envKey, String defValue) {
        String val = System.getenv(envKey);
        return val == null ? defValue : val;
    }

    public static Integer getInt(String envKey, Integer defValue) {
        String val = getStr(envKey, null);
        if (val == null) {
            return defValue;
        }
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return defValue;
        }
    }

}
