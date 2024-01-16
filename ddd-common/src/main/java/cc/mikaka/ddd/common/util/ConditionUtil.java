package cc.mikaka.ddd.common.util;

import java.util.function.Function;

public class ConditionUtil {
    public static <T, R> void addCondition(Function<T, R> exampleOrFun, T condition) {
        if (null != condition) {
            exampleOrFun.apply(condition);
        }
    }
}
