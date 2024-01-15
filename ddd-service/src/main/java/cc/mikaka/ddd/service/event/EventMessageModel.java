package cc.mikaka.ddd.service.event;

import cc.mikaka.ddd.common.enums.ActionType;
import cc.mikaka.ddd.common.enums.BizType;
import lombok.Data;

@Data
public class EventMessageModel {
    /**
     * message_id-消息流水id
     */
    private String messageId;

    /**
     * message_type-消息类型
     */
    private BizType bizType;

    /**
     * action_type-业务类型
     */
    private ActionType actionType;

    /**
     * 数据
     */
    private Object baseEventModel;

    /**
     * 业务数据 == baseEventModel
     * 消息接受的时候使用
     */
    private String bizData;

    /**
     * state-消息状态
     */
    private String state;

    /**
     * retry_times-重试次数
     */
    private Integer retryTimes;
}

