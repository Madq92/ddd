package cc.mikaka.ddd.processor;

import cc.mikaka.ddd.common.enums.ActionType;
import cc.mikaka.ddd.common.enums.BizType;

/**
 * 基础processor
 *
 * @param <Model>
 * @param <Request>
 * @param <BizResult>
 */
public abstract class BaseProcessor<Model, Request, BizResult> {

    /**
     * action
     */
    private ActionType actionType;

    /**
     * biz
     */
    private BizType bizType;

    public abstract BizResult doProcessor(Request request);

    /**
     * Getter method for property <tt>actionType</tt>.
     *
     * @return property value of actionType
     */
    public ActionType getActionType() {
        return actionType;
    }

    /**
     * Setter method for property <tt>actionType</tt>.
     *
     * @param actionType value to be assigned to property actionType
     */
    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    /**
     * Getter method for property <tt>bizType</tt>.
     *
     * @return property value of bizType
     */
    public BizType getBizType() {
        return bizType;
    }

    /**
     * Setter method for property <tt>bizType</tt>.
     *
     * @param bizType value to be assigned to property bizType
     */
    public void setBizType(BizType bizType) {
        this.bizType = bizType;
    }
}
