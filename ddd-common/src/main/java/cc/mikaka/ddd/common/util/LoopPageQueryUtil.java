package cc.mikaka.ddd.common.util;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 循环分页查询
 */
@Slf4j
public class LoopPageQueryUtil {
    /**
     * 循环分页查询
     *
     * @param totalSize
     * @param pageSize
     * @param handler
     * @param <T>
     * @return
     */
    public static <T> List<T> loopPageQuery(Integer totalSize, Integer pageSize, Function<Integer, List<T>> handler) {

        int page = totalSize % pageSize == 0 ? totalSize / pageSize : (totalSize / pageSize) + 1;
        List<T> result = new ArrayList<>();
        for (int i = 1; i <= page; i++) {
            List<T> list = handler.apply(i);
            result.addAll(list);
        }
        return result;
    }
}
