package cc.mikaka.ddd.common.context;

import cc.mikaka.ddd.common.enums.ActionType;
import cc.mikaka.ddd.common.enums.BizType;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ConcurrentMap;

/**
 * 基础上下文
 */
@Getter
@Setter
public class BaseContext {
    private ActionType actionType;
    private BizType bizType;
    private String bizSource;
    private ConcurrentMap<String, Object> extMap = Maps.newConcurrentMap();
    /**
     * 上下文初始化时间
     */
    private Long initTimestamp = System.currentTimeMillis();
}
