package cc.mikaka.ddd.common.util;

import cc.mikaka.ddd.common.error.BizErrorCode;
import cc.mikaka.ddd.common.error.ErrorCode;
import cc.mikaka.ddd.common.exception.BizValidateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.text.MessageFormat;
import java.util.*;

/**
 * 自定义数据断言工具类
 */
public class AssertUtil {
    /**
     * 判断字符串非空，不包含空格符
     */
    public static void notBlank(String text) {
        notBlank(text, BizErrorCode.PARAM_ILLEGAL, BizErrorCode.PARAM_ILLEGAL.getDesc());
    }

    /**
     * 判断字符串非空，不包含空格符
     */
    public static void notBlank(String text, ErrorCode code, String template, Object... parameters) {
        if (StringUtils.isBlank(text)) {
            throw new BizValidateException(code, format(template, parameters));
        }
    }

    /**
     * 判断字符串非空，不包含空格符
     */
    public static void notBlank(String text, String template, Object... parameters) {
        notBlank(text, BizErrorCode.PARAM_ILLEGAL, format(template, parameters));
    }


    /**
     * 校验集合不能为空
     */
    public static void notEmpty(Collection collection, String template, Object... parameters) {
        notEmpty(collection, BizErrorCode.PARAM_ILLEGAL, format(template, parameters));
    }

    /**
     * 判断数组非空
     */
    public static void notEmpty(Object[] array, ErrorCode code, String template, Object... parameters) {
        if (ObjectUtils.isEmpty(array)) {
            throw new BizValidateException(code, format(template, parameters));
        }
    }

    /**
     * 判断集合List非空
     */
    public static void notEmpty(Collection<?> collection, ErrorCode code, String template, Object... parameters) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BizValidateException(code, format(template, parameters));
        }
    }

    /**
     * 判断集合Map非空
     */
    public static void notEmpty(Map<?, ?> map, ErrorCode code, String template, Object... parameters) {
        if (CollectionUtils.isEmpty(map)) {
            throw new BizValidateException(code, format(template, parameters));
        }
    }

    /**
     * 校验集合为空
     */
    public static void isEmpty(Collection collection, String template, Object... parameters) {
        if (!CollectionUtils.isEmpty(collection)) {
            throw new BizValidateException(BizErrorCode.PARAM_ILLEGAL, format(template, parameters));
        }
    }

    /**
     * 判断数组为空
     */
    public static void isEmpty(Object[] array, ErrorCode code, String template, Object... parameters) {
        if (!ObjectUtils.isEmpty(array)) {
            throw new BizValidateException(code, format(template, parameters));
        }
    }

    /**
     * 判断集合List为空
     */
    public static void isEmpty(Collection<?> collection, ErrorCode code, String template, Object... parameters) {
        if (!CollectionUtils.isEmpty(collection)) {
            throw new BizValidateException(code, format(template, parameters));
        }
    }

    /**
     * 判断Optional为空
     */
    public static void isPresent(Optional<?> optional, ErrorCode code, String template, Object... parameters) {
        if (!optional.isPresent()) {
            throw new BizValidateException(code, format(template, parameters));
        }
    }

    /**
     * 判断集合List为非空
     */
    public static void isNotEmpty(Collection<?> collection, ErrorCode code, String template, Object... parameters) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BizValidateException(code, format(template, parameters));
        }
    }

    /**
     * 判断集合Map为空
     */
    public static void isEmpty(Map<?, ?> map, ErrorCode code, String template, Object... parameters) {
        if (!CollectionUtils.isEmpty(map)) {
            throw new BizValidateException(code, format(template, parameters));
        }
    }

    /**
     * 判断对象不为空
     */
    public static void notNull(Object object) {
        if (object == null) {
            throw new BizValidateException(BizErrorCode.PARAM_IS_NULL, BizErrorCode.PARAM_IS_NULL.getDesc());
        }
    }

    /**
     * 判断对象不为空
     */
    public static void notNull(Object object, ErrorCode code) {
        if (object == null) {
            throw new BizValidateException(code, code.getDesc());
        }
    }

    /**
     * 判断对象不为空
     */
    public static void notNull(Object object, String template, Object... parameters) {
        if (object == null) {
            throw new BizValidateException(BizErrorCode.PARAM_IS_NULL, format(template, parameters));
        }
    }


    /**
     * 判断对象非空
     */
    public static void notNull(Object object, ErrorCode code, String template, Object... parameters) {
        if (object == null) {
            throw new BizValidateException(code, format(template, parameters));
        }
    }

    /**
     * 判断是否符合表达式
     */
    public static void isTrue(boolean expression, String template, Object... parameters) {
        if (!expression) {
            throw new BizValidateException(BizErrorCode.PARAM_ILLEGAL, format(template, parameters));
        }
    }

    /**
     * 判断为真
     */
    public static void isTrue(boolean flag, ErrorCode code, String template, Object... parameters) {
        if (!flag) {
            throw new BizValidateException(code, format(template, parameters));
        }
    }

    /**
     * 对象比较
     */
    public static void equals(Object s1, Object s2, ErrorCode code, String template, Object... parameters) {
        if (!Objects.equals(s1, s2)) {
            throw new BizValidateException(code, format(template, parameters));
        }
    }

    /**
     * 对象比较
     */
    public static void notEquals(Object s1, Object s2, ErrorCode code, String template, Object... parameters) {
        if (Objects.equals(s1, s2)) {
            throw new BizValidateException(code, format(template, parameters));
        }
    }

    /**
     * 判断为假
     *
     * @param expression
     * @param template   错误描述
     * @param parameters 参数
     */
    public static void isFalse(boolean expression, String template, Object... parameters) {
        if (expression) {
            throw new BizValidateException(BizErrorCode.PARAM_ILLEGAL, format(template, parameters));
        }
    }

    /**
     * 判断为假
     */
    public static void isFalse(boolean flag, ErrorCode code, String template, Object... parameters) {
        if (flag) {
            throw new BizValidateException(code, format(template, parameters));
        }
    }

    /**
     * 集合数量最大值断言
     *
     * @param collection 断言集合
     * @param max        最大值
     * @param template   错误描述
     * @param parameters 参数
     */
    public static void maxSize(Collection<?> collection, int max, String template, Object... parameters) {
        if (!CollectionUtils.isEmpty(collection) && collection.size() > max) {
            throw new BizValidateException(BizErrorCode.PARAM_ILLEGAL, format(template, parameters));
        }
    }

    /**
     * 最小值断言
     *
     * @param value      待比较值
     * @param minValue   最小值
     * @param template   错误描述
     * @param parameters 参数
     */
    public static void assertMin(int value, int minValue, String template, Object... parameters) {
        if (value < minValue) {
            throw new BizValidateException(BizErrorCode.PARAM_ILLEGAL, format(template, parameters));
        }
    }

    /**
     * 最大值断言
     *
     * @param value      待比较值
     * @param maxValue   最大值
     * @param template   错误描述
     * @param parameters 参数
     */
    public static void assertMax(int value, int maxValue, String template, Object... parameters) {
        if (value > maxValue) {
            throw new BizValidateException(BizErrorCode.PARAM_ILLEGAL, format(template, parameters));
        }
    }

    /**
     * 最大值断言
     *
     * @param value      待比较值
     * @param maxValue   最大值
     * @param template   错误描述
     * @param parameters 参数
     */
    public static void assertMax(int value, int maxValue, ErrorCode errorCode, String template, Object... parameters) {
        if (value > maxValue) {
            throw new BizValidateException(errorCode, format(template, parameters));
        }
    }

    /**
     * 数字判断
     */
    public static void assertNumber(String number, String template, Object... parameters) {
        if (!StringUtils.isNumeric(number)) {
            throw new BizValidateException(BizErrorCode.PARAM_ILLEGAL, format(template, parameters));
        }
    }

    /**
     * 大于零
     */
    public static void greaterThanZero(Integer i, ErrorCode code, String template, Object... parameters) {
        if (null == i || i <= 0) {
            throw new BizValidateException(code, format(template, parameters));
        }
    }

    /**
     * 大于零
     */
    public static void greaterThanZero(Integer i, String template, Object... parameters) {
        greaterThanZero(i, BizErrorCode.PARAM_ILLEGAL, template, parameters);
    }

    /**
     * 大于零
     */
    public static void greaterThanZero(Long l, ErrorCode code, String template, Object... parameters) {
        if (null == l || l <= 0L) {
            throw new BizValidateException(code, format(template, parameters));
        }
    }

    /**
     * 大于零
     */
    public static void greaterThanZero(Double d, ErrorCode code, String template, Object... parameters) {
        if (null == d || d <= 0) {
            throw new BizValidateException(code, format(template, parameters));
        }
    }

    /**
     * 大于等于0
     */
    public static void greaterOrEqualZero(Integer d, ErrorCode code, String template, Object... parameters) {
        if (null == d || d < 0) {
            throw new BizValidateException(code, format(template, parameters));
        }
    }


    /**
     * 大于等于0
     */
    public static void greaterOrEqualZero(Long d, ErrorCode code, String template, Object... parameters) {
        if (null == d || d < 0) {
            throw new BizValidateException(code, format(template, parameters));
        }
    }

    /**
     * 大于等于0
     */
    public static void greaterOrEqualZero(Double d, ErrorCode code, String template, Object... parameters) {
        if (null == d || d < 0) {
            throw new BizValidateException(code, format(template, parameters));
        }
    }

    private static String format(String template, Object... parameters) {
        if (parameters != null && parameters.length != 0) {
            return MessageFormat.format(template, parameters);
        }
        return template;
    }

    /**
     * 检查前缀匹配
     *
     * @param text
     * @param prefixList
     * @param ignoreCase
     * @return
     */
    private static boolean checkPrefixMatch(String text, List<String> prefixList, boolean ignoreCase) {
        if (CollectionUtils.isEmpty(prefixList)) {
            return false;
        }
        for (String prefix : prefixList) {
            int prefixLen = prefix.length();
            int endIndex = text.length() < prefixLen ? text.length() : prefixLen;
            boolean isPrefixMatch;
            if (ignoreCase) {
                isPrefixMatch = text.substring(0, endIndex).toUpperCase().equals(prefix.toUpperCase());
            } else {
                isPrefixMatch = text.substring(0, endIndex).equals(prefix);
            }
            if (isPrefixMatch) {
                return true;
            }
        }
        return false;
    }

}
