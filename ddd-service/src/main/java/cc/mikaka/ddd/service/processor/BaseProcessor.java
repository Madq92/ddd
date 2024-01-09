package cc.mikaka.ddd.service.processor;

import cc.mikaka.ddd.common.enums.ActionType;
import cc.mikaka.ddd.common.enums.BizType;
import lombok.Data;

/**
 * 基础processor
 *
 * @param <Model>
 * @param <Request>
 * @param <BizResult>
 */
@Data
public abstract class BaseProcessor<Model, Request, BizResult> {
    private ActionType actionType;
    private BizType bizType;

    public abstract BizResult doProcessor(Request request);
}
